package com.microsoft.azure.search.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.azure.search.api.AzureSearchRequest;
import com.microsoft.azure.search.api.indexes.CorsOptions;
import com.microsoft.azure.search.api.indexes.Field;
import com.microsoft.azure.search.api.indexes.Index;
import com.moltin.adventure.works.MoltinStore;

public class AzureSearchService {

	static final Logger LOGGER = LoggerFactory.getLogger(AzureSearchService.class);

	public void defineCategoriesIndex() {

		final Index categoriesIndex = new Index()
				.withFields(Arrays.asList(
						new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false)
								.withKey(true).withAnalyzer(null),
						new Field().withName("title").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(true)
								.withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("parent").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "categories").createOrUpdate(categoriesIndex);

	}

	public void defineProductsIndex() {
		final Index productsIndex = new Index()
				.withFields(Arrays.asList(
						new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false)
								.withKey(true).withAnalyzer(null),
						new Field().withName("title").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("category").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true)
								.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("categoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("subcategory").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true)
								.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("subcategoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("modifiers").withType("Collection(Edm.String)").withSearchable(false).withFilterable(false)
								.withRetrievable(true).withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("color").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true)
								.withRetrievable(true).withSortable(false)
								.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("size").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true)
								.withRetrievable(true).withSortable(false)
								.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("sku").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false)
								.withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false)
								.withFacetable(true).withKey(false).withAnalyzer(null),
						new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false)
								.withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "products").createOrUpdate(productsIndex);

	}

	public void defineVariantsIndex() {
		final Index variantsIndex = new Index().withFields(Arrays.asList(
				new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false)
						.withFacetable(false)
						.withKey(true).withAnalyzer(null),
				new Field().withName("productId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
						.withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer(null),
				new Field().withName("color").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
						.withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("size").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(true)
						.withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("sku").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(false)
						.withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true)
						.withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer(null),
				new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
						.withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null),
				new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
						.withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "variants").createOrUpdate(variantsIndex);

	}

	public void deleteCategoriesIndex() {
		new AzureSearchRequest("indexes", "categories").delete();
	}

	public void deleteProductsIndex() {
		new AzureSearchRequest("indexes", "products").delete();
	}

	public void deleteVariantsIndex() {
		new AzureSearchRequest("indexes", "variants").delete();
	}

	public AzureSearchService initialize() {

		return this;
	}

	public void populateIndexes(final MoltinStore moltinStore) {
		populateCategoriesIndex(moltinStore);
		populateProductsVariantsIndex(moltinStore);

	}

	public JsonArray populateCategoriesIndex(final MoltinStore moltinStore) {

		JsonObject categories = moltinStore.getCategories();
		final JsonObject cache = new JsonObject();
		categories.getAsJsonArray("data").forEach(c -> {
			final JsonObject moltinCategory = c.getAsJsonObject();
			cache.add(moltinCategory.get("id").getAsString(), moltinCategory);
		});

		final JsonArray azureCategoriesIndex = new JsonArray();
		categories.getAsJsonArray("data").forEach(c -> {
			final JsonObject moltinCategory = c.getAsJsonObject();

			final JsonObject azureCategoryIndex = new JsonObject();
			azureCategoriesIndex.add(azureCategoryIndex);

			azureCategoryIndex.addProperty("@search.action", "upload");
			azureCategoryIndex.add("id", moltinCategory.get("id"));
			azureCategoryIndex.add("title", moltinCategory.get("name"));
			azureCategoryIndex.add("description", moltinCategory.get("description"));

			if (moltinCategory.has("relationships")) {

				final JsonObject moltinRelationships = moltinCategory.getAsJsonObject("relationships");
				if (moltinRelationships.has("parent")) {

					final JsonObject moltinParent = moltinRelationships.getAsJsonObject("parent");
					moltinParent.getAsJsonArray("data").forEach(_parent -> {
						final JsonObject parent = _parent.getAsJsonObject();
						if ("category".equals(parent.get("type").getAsString())) {
							azureCategoryIndex.add("parent", parent.get("id"));
						}
					});
				}
			}
		});

		final JsonObject body = new JsonObject();
		body.add("value", azureCategoriesIndex);

		new AzureSearchRequest("indexes", "categories", "docs", "index").create(body.toString());

		return azureCategoriesIndex;
	}

	JsonArray getChildProductUUIDs(JsonObject variationMatrix) {
		JsonArray childProducts = new JsonArray();
		variationMatrix.entrySet().forEach(kv -> {
			if (kv.getValue().isJsonPrimitive()) {
				childProducts.add(kv.getValue());
			} else {
				childProducts.addAll(getChildProductUUIDs(kv.getValue().getAsJsonObject()));
			}
		});
		return childProducts;
	}

	public void populateProductsVariantsIndex(final MoltinStore moltinStore) {

		final JsonObject cache = new JsonObject();
		final JsonObject skus = new JsonObject();

		JsonObject products = moltinStore.getProducts();
		JsonObject categories = moltinStore.getCategories();
		JsonObject files = moltinStore.getFiles();

		products.getAsJsonArray("data").forEach(p -> {
			final JsonObject product = p.getAsJsonObject();
			cache.add(product.get("id").getAsString(), product);
			skus.add(product.get("sku").getAsString(), product.get("id"));
		});

		files.getAsJsonArray("data").forEach(f -> {
			final JsonObject file = f.getAsJsonObject();
			cache.add(file.get("id").getAsString(), file);
		});

		categories.getAsJsonArray("data").forEach(c -> {
			final JsonObject category = c.getAsJsonObject();
			cache.add(category.get("id").getAsString(), category);
		});

		final JsonArray azureProductsIndex = new JsonArray();
		final JsonArray azureVariantsIndex = new JsonArray();

		products.getAsJsonArray("data").forEach(p -> {

			final JsonObject moltinProduct = p.getAsJsonObject();
			final JsonObject azureProductIndex = new JsonObject();

			String[] skuParts = moltinProduct.get("sku").getAsString().split("-");
			String productSku = skuParts[0] + "-" + skuParts[1];

			// try to set parent
			moltinProduct.add("productId", skus.get(productSku));

			azureProductIndex.addProperty("@search.action", "upload");
			azureProductIndex.add("id", moltinProduct.get("id"));
			azureProductIndex.add("title", moltinProduct.get("name"));
			azureProductIndex.add("description", moltinProduct.get("description"));
			azureProductIndex.add("price", moltinProduct.getAsJsonArray("price").get(0).getAsJsonObject().get("amount"));
			azureProductIndex.addProperty("sku", productSku);

			LOGGER.debug("added azureProductIndex for id: {}, sku: {}", azureProductIndex.get("id"), azureProductIndex.get("sku"));

			if (moltinProduct.has("relationships")) {

				final JsonObject moltinProductRelationships = moltinProduct.getAsJsonObject("relationships");

				if (moltinProductRelationships.has("main_image")) {
					final JsonObject moltinProductMainImage = moltinProductRelationships.getAsJsonObject("main_image").getAsJsonObject("data");
					if ("main_image".equals(moltinProductMainImage.get("type").getAsString())) {

						final JsonObject moltinImage = cache.getAsJsonObject(moltinProductMainImage.get("id").getAsString());
						if (moltinImage.has("link")) {

							final JsonObject moltinImageLink = moltinImage.getAsJsonObject("link");
							URL moltinImageLinkUrl;
							try {
								moltinImageLinkUrl = new URL(moltinImageLink.get("href").getAsString());
								azureProductIndex.addProperty("image_domain", moltinImageLinkUrl.getHost());
								azureProductIndex.addProperty("image_suffix", moltinImageLinkUrl.getFile());
							} catch (final MalformedURLException e) {
								LOGGER.error("error, while updating products index (for image), e: {}", ExceptionUtils.getStackTrace(e));
							}
						} else {
							LOGGER.error("error, missing link: {} for: {}", moltinProductMainImage.get("id"), moltinProduct.get("id"));
							LOGGER.error("error, object: {}", moltinProductMainImage.toString());
						}
					} else {
						LOGGER.error("no image for id: {}", moltinProduct.get("id"));
					}
				}

				if (moltinProductRelationships.has("categories")) {
					final JsonObject moltinProductCategories = moltinProductRelationships.getAsJsonObject("categories");
					moltinProductCategories.getAsJsonArray("data").forEach(_c -> {
						final JsonObject c = _c.getAsJsonObject();
						if ("category".equals(c.get("type").getAsString())) {
							final JsonObject moltinSubcategory = cache.getAsJsonObject(c.get("id").getAsString());
							azureProductIndex.add("subcategory", moltinSubcategory.get("name"));
							azureProductIndex.add("subcategoryId", moltinSubcategory.get("id"));

							if (moltinSubcategory.has("relationships")) {

								final JsonObject moltinCategoryRelationships = moltinSubcategory.getAsJsonObject("relationships");
								if (moltinCategoryRelationships.has("parent")) {

									final JsonObject moltinCategoryParent = moltinCategoryRelationships.getAsJsonObject("parent");
									moltinCategoryParent.getAsJsonArray("data").forEach(_parent -> {
										final JsonObject parent = _parent.getAsJsonObject();
										if ("category".equals(parent.get("type").getAsString())) {
											final JsonObject moltinCategory = cache.getAsJsonObject(parent.get("id").getAsString());
											azureProductIndex.add("category", moltinCategory.get("name"));
											azureProductIndex.add("categoryId", moltinCategory.get("id"));
										}
									});
								}
							}
						}
					});
				}
			}

			boolean isVariant = !moltinProduct.get("id").equals(moltinProduct.get("productId"));
			
			if (!isVariant) {
				azureProductsIndex.add(azureProductIndex);
				isVariant = !moltinProduct.has("meta");
				if (!isVariant) {
					final JsonObject moltinProductMeta = moltinProduct.getAsJsonObject("meta");
					isVariant = !moltinProductMeta.has("variations");
					if (!isVariant) {
						azureProductIndex.add("modifiers", new JsonArray());
						final JsonArray moltinProductVariations = moltinProductMeta.getAsJsonArray("variations");
						moltinProductVariations.forEach(_v -> {
							final JsonObject v = _v.getAsJsonObject();
							String optionType = v.get("name").getAsString();
							azureProductIndex.getAsJsonArray("modifiers").add(optionType);
							v.getAsJsonArray("options").forEach(_o -> {
								final JsonObject o = _o.getAsJsonObject();
								String optionValue = o.get("name").getAsString();
								if (!azureProductIndex.has(optionType))
									azureProductIndex.add(optionType, new JsonArray());
								azureProductIndex.getAsJsonArray(optionType).add(optionValue);
							});
						});
					}
				}
			}

			if (isVariant) {
				final JsonObject azureVariantIndex = new JsonObject();
				azureVariantIndex.add("id", moltinProduct.get("id"));
				azureVariantIndex.add("productId", moltinProduct.get("productId"));
				azureVariantIndex.addProperty("@search.action", "upload");
				azureVariantIndex.add("image_domain", azureProductIndex.get("image_domain"));
				azureVariantIndex.add("image_suffix", azureProductIndex.get("image_suffix"));
				azureVariantIndex.add("price", moltinProduct.getAsJsonArray("price").get(0).getAsJsonObject().get("amount"));
				azureVariantIndex.addProperty("sku", productSku);

				String description = moltinProduct.get("description").getAsString();
				String[] descriptionTokens = description.split(",");
				for (String descriptionToken : descriptionTokens) {
					if (descriptionToken.contains("=") && descriptionToken.contains("") && descriptionToken.contains("}")) {

						String descriptionToken1 = descriptionToken.replaceAll("\\{", "");
						descriptionToken1 = descriptionToken1.replaceAll("\\}", "");
						String[] attribute = descriptionToken1.split("=");
						azureVariantIndex.addProperty(attribute[0], attribute[1]);

					}
				}
				azureVariantsIndex.add(azureVariantIndex);
			}

		});

		final JsonObject bodyProductsIndex = new JsonObject();
		bodyProductsIndex.add("value", azureProductsIndex);

		new AzureSearchRequest("indexes", "products", "docs", "index").create(bodyProductsIndex.toString());

		final JsonObject bodyVariantsIndex = new JsonObject();
		bodyVariantsIndex.add("value", azureVariantsIndex);

		new AzureSearchRequest("indexes", "variants", "docs", "index").create(bodyVariantsIndex.toString());
	}

}
