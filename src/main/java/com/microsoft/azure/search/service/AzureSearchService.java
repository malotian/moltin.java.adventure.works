package com.microsoft.azure.search.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.azure.search.api.AzureSearchRequest;
import com.microsoft.azure.search.api.indexes.CorsOptions;
import com.microsoft.azure.search.api.indexes.Field;
import com.microsoft.azure.search.api.indexes.Index;

public class AzureSearchService {

	public void defineCategoriesIndex() {

		final Index categoriesIndex = new Index()
				.withFields(Arrays.asList(
						new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(true).withAnalyzer(null),
						new Field().withName("name").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(true).withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("parent").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "categories").createOrUpdate(categoriesIndex);

	}

	public void defineProductsIndex() {
		final Index productsIndex = new Index()
				.withFields(Arrays.asList(
						new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(true).withAnalyzer(null),
						new Field().withName("name").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("category").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("categoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("subcategory").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("subcategoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("modifiers").withType("Collection(Edm.String)").withSearchable(false).withFilterable(false)
								.withRetrievable(true).withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("color").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true)
								.withRetrievable(true).withSortable(false).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("size").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true)
								.withRetrievable(true).withSortable(false).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(true).withKey(false).withAnalyzer(null),
						new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "products").createOrUpdate(productsIndex);

	}

	public void defineVariantsIndex() {
		final Index variantsIndex = new Index()
				.withFields(Arrays.asList(
						new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(true).withAnalyzer(null),
						new Field().withName("productId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("color").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("size").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(true).withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("sku").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer("standard.lucene"),
						new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(true).withFacetable(true).withKey(false).withAnalyzer(null),
						new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null),
						new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true)
								.withSortable(false).withFacetable(false).withKey(false).withAnalyzer(null)))
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

	JsonObject cache = new JsonObject();

	public void populateProductsIndex(final JsonObject products) {
		final JsonArray azureProductsIndex = new JsonArray();
		products.getAsJsonArray("data").forEach(new Consumer<JsonElement>() {
			@Override
			public void accept(JsonElement _c) {
				final JsonObject moltinProduct = _c.getAsJsonObject();
				cache.add("id", moltinProduct);

				final JsonObject azureProductIndex = new JsonObject();
				azureProductsIndex.add(azureProductIndex);

				azureProductIndex.addProperty("@search.action", "upload");
				azureProductIndex.add("id", moltinProduct.get("id"));
				azureProductIndex.add("name", moltinProduct.get("name"));
				azureProductIndex.add("description", moltinProduct.get("description"));

				if (!moltinProduct.has("relationships")) {
					return;
				}

				final JsonObject moltinRelationships = moltinProduct.getAsJsonObject("relationships");
				if (!moltinRelationships.has("parent")) {
					return;
				}

				final JsonObject moltinParent = moltinRelationships.getAsJsonObject("parent");
				moltinParent.getAsJsonArray("data").forEach(new Consumer<JsonElement>() {
					@Override
					public void accept(final JsonElement _parent) {
						final JsonObject parent = _parent.getAsJsonObject();
						if ("category".equals(parent.get("type").getAsString())) {
							azureProductIndex.add("parent", parent.get("id"));
						}
					}
				});

			}
		});

		final JsonObject body = new JsonObject();
		body.add("value", azureProductsIndex);

		new AzureSearchRequest("indexes", "products", "docs", "index").create(body.toString());

	}

	public void populateVariantsIndex(final JsonObject variants) {

	}

}
