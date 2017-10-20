package com.moltin.adventure.works;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moltin.api.v2.AuthenticateRequest;
import com.moltin.api.v2.MoltinRequest;
import com.moltin.api.v2.categories.Category;
import com.moltin.api.v2.categories.Data;
import com.moltin.api.v2.modifiers.Modifier;
import com.moltin.api.v2.options.Option;
import com.moltin.api.v2.products.Price;
import com.moltin.api.v2.products.Product;
import com.moltin.api.v2.relationships.categories.Parent;
import com.moltin.api.v2.relationships.categories.Relationship;
import com.moltin.api.v2.relationships.variations.Datum;
import com.moltin.api.v2.variations.Variation;

public class MoltinStore {

	void deleteCategories() {
		final JsonObject products = new MoltinRequest("categories").get();
		products.getAsJsonArray("data").forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();
			new MoltinRequest("categoriesS", product.get("id").getAsString()).delete();
		});
	}

	void deleteProducts() {
		final JsonObject products = new MoltinRequest("products").get();
		products.getAsJsonArray("data").forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();
			new MoltinRequest("products", product.get("id").getAsString()).delete();
		});
	}

	MoltinStore initialize() {
		final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.request();
		return this;
	}

	//@formatter:off
	public void populate(AdventureWorksData awd) throws IOException {
		final JsonObject cache = new JsonObject();

		final JsonArray productCategories = awd.read("ProductCategory.csv", CSVFormat.TDF.withHeader("id", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productCategories.forEach(_c -> {
			final JsonObject c = _c.getAsJsonObject();
			final Category category = new Category()
					.withData(new Data()
							.withType("category")
							.withName(c.get("name").getAsString())
							.withDescription(c.get("name").getAsString())
							.withSlug(c.get("name").getAsString())
							.withStatus("live"));

			final String uuidCategory = new MoltinRequest("categories").create(category).get("data").getAsJsonObject().get("id").getAsString();
			cache.addProperty(c.get("id").getAsString(), uuidCategory);
		});

		final JsonArray productSubcategories = awd.read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productSubcategories.forEach(_sc -> {
			final JsonObject sc = _sc.getAsJsonObject();
			final Category category = new Category()
					.withData(new Data()
							.withType("category")
							.withName(sc.get("name").getAsString())
							.withDescription(sc.get("name").getAsString())
							.withSlug(sc.get("name").getAsString().toLowerCase())
							.withStatus("live"));
			final String uuidSubcategory = new MoltinRequest("categories").create(category).get("data").getAsJsonObject().get("id").getAsString();

			//Datum datum = new Datum().withType("category").withId(sc.get("parent").toString());
			final Relationship relationship = new Relationship()
					.withData(new com.moltin.api.v2.relationships.categories.Data()
							.withParent(new Parent()
									.withId(cache.get(sc.get("parent").getAsString()).getAsString())
									.withType("category")));


			new MoltinRequest("categories", uuidSubcategory, "relationships", "categories").create(relationship);

		});

		final JsonObject categoriesList = new MoltinRequest("categories").get();
		final JsonObject productList = new MoltinRequest("products").get();
		productList.getAsJsonArray("data").forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();
			new MoltinRequest("products", product.get("id").getAsString()).delete();
		});

		awd.getInventory().forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();

			if (product.getAsJsonArray("variants").size() <= 0) {
				return;
			}

			final JsonObject variant = product.getAsJsonArray("variants").get(0).getAsJsonObject();
			categoriesList.getAsJsonArray("data").forEach(_category -> {
				final JsonObject category = _category.getAsJsonObject();
				if (variant.getAsJsonObject("category").get("name").equals(category.get("name"))) {
					variant.getAsJsonObject("category").add("uuid", category.get("id"));
				}
			});

			final Product cp = new Product().withData(new com.moltin.api.v2.products.Data()
					/*.withWeight(new Weight().withUnit("kg")
							.withValue("1"))*/
					.withPrice(Arrays.asList(new Price()
							.withAmount((int)variant.get("price").getAsDouble() * 100)
							.withCurrency("USD")
							.withIncludesTax(true)))
					.withStatus("live")
					.withDescription(product.get("name").getAsString())
					.withManageStock(true)
					.withName(product.get("name").getAsString())
					/*.withDimensions(Arrays.asList(new Dimension()
							.withMeasurement("length")
							.withUnit("cm")
							.withValue(variant.get("size").getAsString()),
							new Dimension()
							.withMeasurement("width")
							.withUnit("cm")
							.withValue(variant.get("size").getAsString()),
							new Dimension()
							.withMeasurement("height")
							.withUnit("cm")
							.withValue(variant.get("size").getAsString())))*/
					.withCommodityType("physical")
					.withSlug(product.get("name").getAsString().toLowerCase().replace(" ", "-"))
					.withSku(variant.get("sku").getAsString().substring(0, 7))
					.withType("product"));

			final String uuidProduct = new MoltinRequest("products").create(cp).get("data").getAsJsonObject().get("id").getAsString();
			product.getAsJsonArray("modifiers").forEach(_modifier -> {
				final JsonObject modifier = _modifier.getAsJsonObject();
				final Variation variation = new Variation()
						.withData(new com.moltin.api.v2.variations.Data()
								.withName(modifier.get("title").getAsString())
								.withType("product-variation"));
				final String uuidVariation = new MoltinRequest("variations").create(variation).get("data").getAsJsonObject().get("id").getAsString();
				final com.moltin.api.v2.relationships.variations.Relationship relationship = new com.moltin.api.v2.relationships.variations.Relationship();


				modifier.getAsJsonArray("values").forEach(value -> {

					final Option option = new Option()
							.withData(new com.moltin.api.v2.options.Data()
									.withName(value.getAsString())
									.withType("product-variation-option")
									.withDescription(value.getAsString() + " " + product.get("name").getAsString()));

					final JsonObject variationOption = new MoltinRequest("variations", uuidVariation, "variation-options").create(option);
					variationOption.getAsJsonObject("data").getAsJsonArray("options").forEach(_vo -> {
						final JsonObject vo = _vo.getAsJsonObject();
						if (vo.get("name").equals(value))
						{
							final Modifier productModifier = new Modifier()
									.withData(new com.moltin.api.v2.modifiers.Data()
											.withType("product-modifier")
											.withModifierType("name_append")
											.withValue(value.getAsString()));

							final String uuidOption = vo.get("id").getAsString();
							final String uuidProductModifier = new MoltinRequest("variations", uuidVariation, "variation-options" ,uuidOption ,"product-modifiers").create(productModifier).get("data").getAsJsonObject().get("id").getAsString();
							relationship.getData().add(new Datum()
											.withType("product-variation")
											.withId(uuidProductModifier));

						}
					});
				});

				@SuppressWarnings("unused")
				final JsonObject productModifier = new MoltinRequest("products", uuidProduct, "relationships", "variations").create(relationship);

			});
		});
	}
	//@formatter:off
}
