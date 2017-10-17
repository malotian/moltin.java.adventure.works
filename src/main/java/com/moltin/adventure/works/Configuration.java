/**
 */
package com.moltin.adventure.works;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static final String APPLICATION_PROPERTIES_FILENAME = "application.properties";
	private static final String DEBUG_PROPERTIES_FILENAME = "debug.properties";
	private static final String DEBUG_PROPERTY_PREFIX = "debug.";

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	PropertiesConfiguration properties = new PropertiesConfiguration();

	PropertiesConfiguration transformations = new PropertiesConfiguration();

	public String getAuthenticateServiceURL() {
		final String key = (isRestSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "mywizard.token.ws.url";
		return properties.getString(key);
	}

	public String getDBConnectionURL() {
		final String key = (isDbSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "hpsm.db.url";
		return properties.getString(key);
	}

	public String getDBPassword() {
		final String key = (isDbSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "hpsm.db.password";
		try {
			return Util.decryptText(properties.getString(key));
		} catch (final Exception e) {
			LOGGER.error("error, loading: {}", ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public String getDBSchemaName() {
		final String key = (isDbSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "hpsm.db.schema.name";
		return properties.getString(key, "");
	}

	public String getDBUsername() {
		final String key = (isDbSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "hpsm.db.username";
		return properties.getString(key);
	}

	public int getEventStatsInterval() {
		return properties.getInt("hpsm.print.event.stats.every.ms");
	}

	public String getIncidentsServiceURL() {
		final String key = (isRestSimulationOn() ? DEBUG_PROPERTY_PREFIX : StringUtils.EMPTY) + "mywizard.incident.ws.url";
		return properties.getString(key);
	}

	public int getMaxRetryCount() {
		return properties.getInt("hpsm.ws.max.retry.count", 0);
	}

	public int getRestSimulationBindPort() {
		return properties.getInt("debug.hpsm.rest.simulation.port", -1);
	}

	public long getRetryDelay() {
		return properties.getLong("hpsm.retry.delay.ms", 0);
	}

	public String getSenderApp() {
		final String key = "mywizard.sender.app";
		return properties.getString(key);
	}

	public String getSenderUser() {
		final String key = "mywizard.sender.user";
		return properties.getString(key);
	}

	public int getSimulationEventsCount() {
		return properties.getInt("debug.hpsm.event.simulation.count", -1);
	}

	public String getSourceId() {
		return properties.getString("mywizard.source.id");
	}

	public PropertiesConfiguration getTransformations() {
		return transformations;

	}

	public String getVender() {
		final String key = "mywizard.vendor.name";
		return properties.getString(key);
	}

	public Configuration initialize() throws ConfigurationException {

		properties.clear();

		if (Files.exists(Paths.get(Util.MOLTIN_AW_HOME, DEBUG_PROPERTIES_FILENAME))) {
			properties.load(new File(Util.MOLTIN_AW_HOME, DEBUG_PROPERTIES_FILENAME));
		}

		properties.load(new File(Util.MOLTIN_AW_HOME, APPLICATION_PROPERTIES_FILENAME));
		LOGGER.debug("properties Loaded");
		return this;
	}

	public boolean isDbSimulationOn() {
		return properties.getBoolean("debug.hpsm.db.simulation", false);
	}

	public boolean isEventSimulationOn() {
		return properties.getBoolean("debug.hpsm.event.simulation", false);
	}

	public boolean isRestSimulationOn() {
		return properties.getBoolean("debug.hpsm.rest.simulation", false);
	}

	public boolean shallShowEventStats() {
		return properties.containsKey("hpsm.print.event.stats.every.ms");
	}

	public boolean shallSimulationRestFailuresRandomly() {
		return properties.getBoolean("debug.hpsm.rest.simulation.fail.randomly", false);
	}

	public boolean shallTruncateSimulationEventsAtStart() {
		return properties.getBoolean("debug.hpsm.event.truncate.at.start", false);
	}

}
