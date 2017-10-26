/**
 */
package com.moltin.adventure.works.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static final String APPLICATION_PROPERTIES_FILENAME = "application.properties";
	private static final String DEBUG_PROPERTIES_FILENAME = "debug.properties";
	// private static final String DEBUG_PROPERTY_PREFIX = "debug.";

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	PropertiesConfiguration properties = new PropertiesConfiguration();

	public String getAdventureWorksDataLocation() {
		return properties.getString("adventure.works.data.location");
	}

	public String getAzureSearchApiKey() {
		return properties.getString("azure.search.api.key");
	}

	public String getAzureSearchAppName() {
		return properties.getString("azure.serach.app.name");
	}

	public String getMoltinApiClientID() {
		return properties.getString("moltin.api.client.id");
	}

	public String getMoltinApiClientSecret() {
		return properties.getString("moltin.api.client.secret");
	}

	public Configuration initialize() throws ConfigurationException {

		properties.clear();

		if (Files.exists(Paths.get(Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME, DEBUG_PROPERTIES_FILENAME))) {
			properties.load(new File(Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME, DEBUG_PROPERTIES_FILENAME));
		}

		properties.load(new File(Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME, APPLICATION_PROPERTIES_FILENAME));
		LOGGER.debug("properties Loaded");
		return this;
	}

}
