package com.moltin.adventure.works;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moltin.api.v2.AuthenticateRequest;
import com.moltin.api.v2.MoltinRequest;
import com.moltin.api.v2.categories.Category;
import com.moltin.api.v2.categories.Data;
import com.moltin.api.v2.categories.relationships.categories.Parent;
import com.moltin.api.v2.modifiers.Modifier;
import com.moltin.api.v2.options.Option;
import com.moltin.api.v2.products.Price;
import com.moltin.api.v2.products.Product;
import com.moltin.api.v2.products.relationships.variations.Datum;
import com.moltin.api.v2.variations.Variation;

public class MoltinStore {

	private static final Logger LOGGER = LoggerFactory.getLogger(MoltinStore.class);

	public void deleteCategories() {
		LOGGER.debug("deleting categories");
		final JsonObject products = new MoltinRequest("categories").get();
		products.getAsJsonArray("data").forEach(_category -> {
			final JsonObject category = _category.getAsJsonObject();
			new MoltinRequest("categories", category.get("id").getAsString()).delete();
		});
	}

	public void deleteFiles() {
		LOGGER.debug("deleting files");
		final JsonObject products = new MoltinRequest("files").get();
		products.getAsJsonArray("data").forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();
			new MoltinRequest("files", product.get("id").getAsString()).delete();
		});
	}

	public void deleteProducts() {
		LOGGER.debug("deleting products");
		final JsonObject products = new MoltinRequest("products").get();
		products.getAsJsonArray("data").forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();
			new MoltinRequest("products", product.get("id").getAsString()).delete();
		});
	}

	public JsonObject getCategories() {
		return new MoltinRequest("categories").get();
	}

	public JsonObject getFiles() {
		return new MoltinRequest("files").get();
	}

	public JsonObject getProducts() {
		return new MoltinRequest("products").get();
	}
	
	public JsonObject getProduct(String uuid) {
		return new MoltinRequest("products", uuid).get();
	}


	public MoltinStore initialize() {
		final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.request();
		return this;
	}

	public void populate(final AdventureWorksData awd) throws IOException {
		final JsonObject cache = new JsonObject();

		final JsonArray productCategories = awd.read("ProductCategory.csv", CSVFormat.TDF.withHeader("id", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productCategories.forEach(_csvCategory -> {
			final JsonObject csvCategory = _csvCategory.getAsJsonObject();
			final Category category = new Category().withData(new Data().withType("category").withName(csvCategory.get("name").getAsString())
					.withDescription(csvCategory.get("name").getAsString()).withSlug(csvCategory.get("name").getAsString()).withStatus("live"));

			final String moltinCategoryUUID = new MoltinRequest("categories").create(category).get("data").getAsJsonObject().get("id").getAsString();
			cache.addProperty(csvCategory.get("id").getAsString(), moltinCategoryUUID);
		});

		final JsonArray productSubcategories = awd.read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productSubcategories.forEach(_csvSubcategory -> {
			final JsonObject csvSubCategory = _csvSubcategory.getAsJsonObject();
			final Category subcategory = new Category().withData(new Data().withType("category").withName(csvSubCategory.get("name").getAsString())
					.withDescription(csvSubCategory.get("name").getAsString()).withSlug(csvSubCategory.get("name").getAsString().toLowerCase()).withStatus("live"));
			final String moltinSubcategoryUUID = new MoltinRequest("categories").create(subcategory).get("data").getAsJsonObject().get("id").getAsString();

			final com.moltin.api.v2.categories.relationships.categories.Relationship relationshipCategoryCategory = new com.moltin.api.v2.categories.relationships.categories.Relationship()
					.withData(new com.moltin.api.v2.categories.relationships.categories.Data()
							.withParent(new Parent().withId(cache.get(csvSubCategory.get("parent").getAsString()).getAsString()).withType("category")));

			new MoltinRequest("categories", moltinSubcategoryUUID, "relationships", "categories").create(relationshipCategoryCategory);

		});

		final JsonObject moltinCategories = new MoltinRequest("categories").get();

		awd.getProducts().forEach(_csvProduct -> {
			final JsonObject csvProduct = _csvProduct.getAsJsonObject();

			LOGGER.warn("product/variant: {}", csvProduct);
			final JsonObject csvFirstProductVariant = csvProduct.getAsJsonArray("variants").size() > 0 ? csvProduct.getAsJsonArray("variants").get(0).getAsJsonObject()
					: csvProduct;

			if (!csvFirstProductVariant.has("sku")) {
				LOGGER.warn("skipping, not a valid product/variant: {}", csvFirstProductVariant);
				return;
			}

			final Product product = new Product().withData(new com.moltin.api.v2.products.Data()
					.withPrice(Arrays.asList(new Price().withAmount(csvFirstProductVariant.has("price") ? (int) csvFirstProductVariant.get("price").getAsDouble() * 100 : 1 * 100)
							.withCurrency("USD").withIncludesTax(true)))
					.withStatus("live").withDescription(csvProduct.get("name").getAsString()).withManageStock(true).withName(csvProduct.get("name").getAsString())
					.withCommodityType("physical").withSlug(csvProduct.get("name").getAsString().toLowerCase().replace(" ", "-"))
					.withSku(csvFirstProductVariant.get("sku").getAsString().substring(0, 7)).withType("product"));

			final String uuidProduct = new MoltinRequest("products").create(product).get("data").getAsJsonObject().get("id").getAsString();
			final com.moltin.api.v2.products.relationships.categories.Relationship relationshipProductCategory = new com.moltin.api.v2.products.relationships.categories.Relationship();

			if (!product.getData().getDescription().equals("aaaaaaaaa"))
				return;
			AtomicReference<Boolean> shallBuildChildProducts = new AtomicReference<>();
			shallBuildChildProducts.set(new Boolean(false));

			moltinCategories.getAsJsonArray("data").forEach(_moltinCategory -> {
				final JsonObject moltinCategory = _moltinCategory.getAsJsonObject();
				if (csvFirstProductVariant.getAsJsonObject("category").get("name").equals(moltinCategory.get("name"))) {
					relationshipProductCategory.getData()
							.add(new com.moltin.api.v2.products.relationships.categories.Datum().withId(moltinCategory.get("id").getAsString()).withType("category"));
				}
			});

			new MoltinRequest("products", uuidProduct, "relationships", "categories").create(relationshipProductCategory);

			if (csvFirstProductVariant.has("image")) {
				final File largeImageFile = new File(new File(awd.getDirectory().toFile(), "images"),
						csvFirstProductVariant.getAsJsonObject("image").get("large_filename").getAsString().replace("gif", "png"));
				final String uuidLargeImage = new MoltinRequest("files").file(largeImageFile).get("data").getAsJsonObject().get("id").getAsString();

				final com.moltin.api.v2.products.relationships.images.Relationship relationshipMainImage = new com.moltin.api.v2.products.relationships.images.Relationship()
						.withData(new com.moltin.api.v2.products.relationships.images.Data().withId(uuidLargeImage).withType("main_image"));
				new MoltinRequest("products", uuidProduct, "relationships", "main-image").create(relationshipMainImage);

				final File thumbnailImageFile = new File(new File(awd.getDirectory().toFile(), "images"),
						csvFirstProductVariant.getAsJsonObject("image").get("thumbnail_filename").getAsString().replace("gif", "png"));
				final String uuidThumbnailImage = new MoltinRequest("files").file(thumbnailImageFile).get("data").getAsJsonObject().get("id").getAsString();

				final com.moltin.api.v2.products.relationships.files.Relationship relationshipFile = new com.moltin.api.v2.products.relationships.files.Relationship()
						.withData(Arrays.asList(new com.moltin.api.v2.products.relationships.files.Datum().withId(uuidThumbnailImage).withType("file")));

				new MoltinRequest("products", uuidProduct, "relationships", "files").create(relationshipFile);

			}

			csvProduct.getAsJsonArray("modifiers").forEach(_csvProductModifier -> {
				final JsonObject csvProductModifier = _csvProductModifier.getAsJsonObject();
				final Variation variation = new Variation()
						.withData(new com.moltin.api.v2.variations.Data().withName(csvProductModifier.get("title").getAsString()).withType("product-variation"));

				final String moltinProductVariationUUID = new MoltinRequest("variations").create(variation).get("data").getAsJsonObject().get("id").getAsString();
				final com.moltin.api.v2.products.relationships.variations.Relationship relationshipProductVariation = new com.moltin.api.v2.products.relationships.variations.Relationship();

				csvProductModifier.getAsJsonArray("values").forEach(csvProductModifierValue -> {
					final Option option = new Option().withData(new com.moltin.api.v2.options.Data().withName(csvProductModifierValue.getAsString())
							.withType("product-variation-option").withDescription(csvProductModifierValue.getAsString() + " " + csvProduct.get("name").getAsString()));

					final JsonObject moltinProductVariationVariationOptions = new MoltinRequest("variations", moltinProductVariationUUID, "variation-options").create(option);

					moltinProductVariationVariationOptions.getAsJsonObject("data").getAsJsonArray("options").forEach(_moltinProductVariationVariationOption -> {
						final JsonObject moltinProductVariationVariationOption = _moltinProductVariationVariationOption.getAsJsonObject();

						if (moltinProductVariationVariationOption.get("name").equals(csvProductModifierValue)) {

							final String productModifierSuffix = csvProductModifierValue.getAsString().replaceAll("/", "-").replaceAll(" ", "-");

							final Modifier productModifierNameAppend = new Modifier().withData(
									new com.moltin.api.v2.modifiers.Data().withType("product-modifier").withModifierType("name_append").withValue("-" + productModifierSuffix));

							final Modifier productModifierSkuAppend = new Modifier().withData(
									new com.moltin.api.v2.modifiers.Data().withType("product-modifier").withModifierType("sku_append").withValue("-" + productModifierSuffix));

							final Modifier productModifierSlugAppend = new Modifier().withData(
									new com.moltin.api.v2.modifiers.Data().withType("product-modifier").withModifierType("slug_append").withValue("-" + productModifierSuffix));

							final Modifier productModifierDescriptionAppend = new Modifier().withData(new com.moltin.api.v2.modifiers.Data().withType("product-modifier")
									.withModifierType("description_append").withValue(",{" + csvProductModifier.get("title").getAsString() + "=" + productModifierSuffix + "}"));

							final String moltinProductVariationVariationOptionUUID = moltinProductVariationVariationOption.get("id").getAsString();

							final String moltinProductVariationVariationOptionProductModifierNameAppendUUID = new MoltinRequest("variations", moltinProductVariationUUID,
									"variation-options", moltinProductVariationVariationOptionUUID, "product-modifiers").create(productModifierNameAppend).get("data")
											.getAsJsonObject().get("id").getAsString();

							final String moltinProductVariationVariationOptionProductModifierSkuAppendUUID = new MoltinRequest("variations", moltinProductVariationUUID,
									"variation-options", moltinProductVariationVariationOptionUUID, "product-modifiers").create(productModifierSkuAppend).get("data")
											.getAsJsonObject().get("id").getAsString();

							final String moltinProductVariationVariationOptionProductModifierSlugAppendUUID = new MoltinRequest("variations", moltinProductVariationUUID,
									"variation-options", moltinProductVariationVariationOptionUUID, "product-modifiers").create(productModifierSlugAppend).get("data")
											.getAsJsonObject().get("id").getAsString();

							final String moltinProductVariationVariationOptionProductModifierDescriptionUUID = new MoltinRequest("variations", moltinProductVariationUUID,
									"variation-options", moltinProductVariationVariationOptionUUID, "product-modifiers").create(productModifierDescriptionAppend).get("data")
											.getAsJsonObject().get("id").getAsString();

							relationshipProductVariation.getData()
									.add(new Datum().withType("product-variation").withId(moltinProductVariationVariationOptionProductModifierNameAppendUUID));
							relationshipProductVariation.getData()
									.add(new Datum().withType("product-variation").withId(moltinProductVariationVariationOptionProductModifierSkuAppendUUID));
							relationshipProductVariation.getData()
									.add(new Datum().withType("product-variation").withId(moltinProductVariationVariationOptionProductModifierSlugAppendUUID));
							relationshipProductVariation.getData()
									.add(new Datum().withType("product-variation").withId(moltinProductVariationVariationOptionProductModifierDescriptionUUID));

							shallBuildChildProducts.set(new Boolean(true));
						}
					});
				});

				new MoltinRequest("products", uuidProduct, "relationships", "variations").create(relationshipProductVariation);

			});

			//if (shallBuildChildProducts.get())
				//new MoltinRequest("products", uuidProduct, "build").create(null);
		});
	}
}
