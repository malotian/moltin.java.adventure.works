package com.moltin.adventure.works;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.LogManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moltin.api.v2.AuthenticateRequest;
import com.moltin.api.v2.CategoryTreeRequest;
import com.moltin.api.v2.CreateCategoryCategoryRelationshipRequest;
import com.moltin.api.v2.CreateCategoryRequest;
import com.moltin.api.v2.categories.Category;
import com.moltin.api.v2.categories.Data;
import com.moltin.api.v2.relationships.Parent;
import com.moltin.api.v2.relationships.ParentRelationship;

public class Driver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);
	static {
		try {
			if (Files.exists(Paths.get(Util.MOLTIN_AW_HOME, "log4j.properties"))) {
				final Properties props = new Properties();
				props.load(new FileInputStream(new File(Util.MOLTIN_AW_HOME, "log4j.properties")));
				PropertyConfigurator.configure(props);
			}

			LogManager.getLogManager().reset();
			SLF4JBridgeHandler.install();

		} catch (final Exception e) {
			LOGGER.error("error: {}", ExceptionUtils.getStackTrace(e));
		}
	}

	public static void main(final String[] args) {
		try {

			final AdventureWorksData awd = new AdventureWorksData(Paths.get("moltin-data"));
			awd.initialize();

			LOGGER.info("using moltin.aw.home: {}", Util.MOLTIN_AW_HOME);
			final Driver broker = new Driver();
			Context.put(Driver.class, broker.iniatilize());
			awd.dump();
			processCategories(awd);

		} catch (final Exception e) {
			LOGGER.error("error: {}", ExceptionUtils.getStackTrace(e));
		}
	}

	public static void processCategories(AdventureWorksData awd) throws IOException {
		final JsonObject cache = new JsonObject();

		final JsonArray productCategories = awd.read("ProductCategory.csv", CSVFormat.TDF.withHeader("id", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productCategories.forEach(_c -> {
			final JsonObject c = _c.getAsJsonObject();
			//@formatter:off
			final Category category = new Category()
					.withData(new Data()
							.withType("category")
							.withName(c.get("name").getAsString())
							.withDescription(c.get("name").getAsString())
							.withSlug(c.get("name").getAsString())
							.withStatus("live"));

			final CreateCategoryRequest ccr = new CreateCategoryRequest();
			final String uuid = ccr.request(category).get("data").getAsJsonObject().get("id").getAsString();
			cache.addProperty(c.get("id").getAsString(), uuid);
			//@formatter:on
		});

		final JsonArray productSubcategories = awd.read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII);
		productSubcategories.forEach(_sc -> {
			final JsonObject sc = _sc.getAsJsonObject();
			//@formatter:off
			final Category category = new Category()
					.withData(new Data()
							.withType("category")
							.withName(sc.get("name").getAsString())
							.withDescription(sc.get("name").getAsString())
							.withSlug(sc.get("name").getAsString().toLowerCase())
							.withStatus("live"));
			final CreateCategoryRequest ccr = new CreateCategoryRequest();
			final String uuid = ccr.request(category).get("data").getAsJsonObject().get("id").getAsString();

			//Datum datum = new Datum().withType("category").withId(sc.get("parent").toString());
			final ParentRelationship relationship = new ParentRelationship()
					.withData(new com.moltin.api.v2.relationships.Data()
							.withParent(new Parent()
									.withId(cache.get(sc.get("parent").getAsString()).getAsString())
									.withType("category")));
			final CreateCategoryCategoryRelationshipRequest ccrr = new CreateCategoryCategoryRelationshipRequest();
			ccrr.request(uuid, relationship);

			//@formatter:on
		});

		final JsonObject categoriesT = new CategoryTreeRequest().request();
		awd.getInventory().forEach(_product -> {
			final JsonObject product = _product.getAsJsonObject();

		});

	}

	final Configuration configuration = new Configuration();

	final ScheduledThreadPoolExecutor statsExecutor = new ScheduledThreadPoolExecutor(1);

	public Driver iniatilize() throws ConfigurationException {
		Context.put(Configuration.class, configuration.initialize());

		final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
		authenticateRequest.request();

		return this;
	}

}
