package com.microsoft.azure.search.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.azure.search.api.AzureSearchRequest;
import com.microsoft.azure.search.api.indexes.CorsOptions;
import com.microsoft.azure.search.api.indexes.Field;
import com.microsoft.azure.search.api.indexes.Index;

public class AzureSearchService {

	static final Logger LOGGER = LoggerFactory.getLogger(AzureSearchService.class);

	JsonObject cache = new JsonObject();

	public void defineCategoriesIndex() {

		final Index categoriesIndex = new Index().withFields(Arrays.asList(
				new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(true).withAnalyzer(null),
				new Field().withName("name").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true).withSortable(true).withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("parent").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "categories").createOrUpdate(categoriesIndex);

	}

	public void defineProductsIndex() {
		final Index productsIndex = new Index().withFields(Arrays.asList(
				new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(true).withAnalyzer(null),
				new Field().withName("name").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("description").withType("Edm.String").withSearchable(true).withFilterable(false).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("category").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("categoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer(null),
				new Field().withName("subcategory").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("subcategoryId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null),
				new Field().withName("variations").withType("Collection(Edm.String)").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null),
				new Field().withName("color").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(false)
						.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("size").withType("Collection(Edm.String)").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(false)
						.withFacetable(true).withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false).withFacetable(true)
						.withKey(false).withAnalyzer(null),
				new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null),
				new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null)))
				.withCorsOptions(new CorsOptions().withAllowedOrigins(Arrays.asList("*")).withMaxAgeInSeconds(30));

		new AzureSearchRequest("indexes", "products").createOrUpdate(productsIndex);

	}

	public void defineVariantsIndex() {
		final Index variantsIndex = new Index().withFields(Arrays.asList(
				new Field().withName("id").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(true).withAnalyzer(null),
				new Field().withName("productId").withType("Edm.String").withSearchable(false).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer(null),
				new Field().withName("color").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("size").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("sku").withType("Edm.String").withSearchable(true).withFilterable(true).withRetrievable(true).withSortable(false).withFacetable(false)
						.withKey(false).withAnalyzer("standard.lucene"),
				new Field().withName("price").withType("Edm.Double").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(true).withFacetable(true)
						.withKey(false).withAnalyzer(null),
				new Field().withName("image_domain").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false)
						.withFacetable(false).withKey(false).withAnalyzer(null),
				new Field().withName("image_suffix").withType("Edm.String").withSearchable(false).withFilterable(false).withRetrievable(true).withSortable(false)
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

	public void populateCategoriesIndex(final JsonObject categorys) {
		final JsonArray azureCategoriesIndex = new JsonArray();
		categorys.getAsJsonArray("data").forEach(c -> {
			final JsonObject moltinCategory = c.getAsJsonObject();
			cache.add("id", moltinCategory);

			final JsonObject azureCategoryIndex = new JsonObject();
			azureCategoriesIndex.add(azureCategoryIndex);

			azureCategoryIndex.addProperty("@search.action", "upload");
			azureCategoryIndex.add("id", moltinCategory.get("id"));
			azureCategoryIndex.add("name", moltinCategory.get("name"));
			azureCategoryIndex.add("description", moltinCategory.get("description"));

			if (!moltinCategory.has("relationships")) {
				return;
			}

			final JsonObject moltinRelationships = moltinCategory.getAsJsonObject("relationships");
			if (!moltinRelationships.has("parent")) {
				return;
			}

			final JsonObject moltinParent = moltinRelationships.getAsJsonObject("parent");
			moltinParent.getAsJsonArray("data").forEach(_parent -> {
				final JsonObject parent = _parent.getAsJsonObject();
				if ("category".equals(parent.get("type").getAsString())) {
					azureCategoryIndex.add("parent", parent.get("id"));
				}
			});

		});

		final JsonObject body = new JsonObject();
		body.add("value", azureCategoriesIndex);

		new AzureSearchRequest("indexes", "categories", "docs", "index").create(body.toString());
	}

	public void populateProductsIndex(final JsonObject products) {
		final JsonArray azureProductsIndex = new JsonArray();
		products.getAsJsonArray("data").forEach(p -> {
			final JsonObject moltinProduct = p.getAsJsonObject();

			if (moltinProduct.has("variations"))
				return;

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

			final JsonObject moltinProductRelationships = moltinProduct.getAsJsonObject("relationships");
			if (!moltinProductRelationships.has("categories")) {
				return;
			}

			final JsonObject moltinProductCategories = moltinProductRelationships.getAsJsonObject("categories");
			moltinProductCategories.getAsJsonArray("data").forEach(_c -> {
				final JsonObject c = _c.getAsJsonObject();
				if ("category".equals(c.get("type").getAsString())) {
					final JsonObject moltinSubcategory = cache.getAsJsonObject(c.get("id").getAsString());
					azureProductIndex.add("subcategory", moltinSubcategory.get("name"));
					azureProductIndex.add("subcategoryId", moltinSubcategory.get("id"));

					if (!moltinSubcategory.has("relationships")) {
						return;
					}

					final JsonObject moltinCategoryRelationships = moltinSubcategory.getAsJsonObject("relationships");
					if (!moltinCategoryRelationships.has("parent")) {
						return;
					}

					final JsonObject moltinCategoryParent = moltinCategoryRelationships.getAsJsonObject("parent");
					moltinCategoryParent.getAsJsonArray("data").forEach(_parent -> {
						final JsonObject parent = _parent.getAsJsonObject();
						if ("category".equals(parent.get("type").getAsString())) {
							final JsonObject moltinCategory = cache.getAsJsonObject(c.get("id").getAsString());
							azureProductIndex.add("subcategory", moltinCategory.get("name"));
							azureProductIndex.add("subcategoryId", moltinCategory.get("id"));
						}
					});
				}
			});

			if (!moltinProduct.has("variations")) {
				return;
			}

			final JsonArray colors = new JsonArray();
			final JsonArray variations = new JsonArray();
			final JsonArray sizes = new JsonArray();

			final JsonArray moltinProductVariations = moltinProduct.getAsJsonArray("variations");
			moltinProductVariations.forEach(_v -> {
				final JsonObject v = _v.getAsJsonObject();
				variations.add(v.get("name"));
				v.getAsJsonArray("options").forEach(_o -> {
					final JsonObject o = _o.getAsJsonObject();
					if (v.get("name").getAsString().equals("size")) {
						colors.add(o.get("name").getAsString());
					} else if (v.get("size").getAsString().equals("size")) {
						sizes.add(o.get("name").getAsString());
					} else {
						LOGGER.warn("verify data, invalid variation : {}", v);
					}
				});
			});

			azureProductIndex.add("variations", colors);
			azureProductIndex.add("color", colors);
			azureProductIndex.add("size", sizes);

		});

		final JsonObject body = new JsonObject();
		body.add("value", azureProductsIndex);

		new AzureSearchRequest("indexes", "products", "docs", "index").create(body.toString());
	}

}
