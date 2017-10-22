package com.moltin.adventure.works.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.LogManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.moltin.adventure.works.AdventureWorksData;
import com.moltin.adventure.works.MoltinStore;

public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	static {
		try {
			if (Files.exists(Paths.get(Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME, "log4j.properties"))) {
				final Properties props = new Properties();
				props.load(new FileInputStream(new File(Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME, "log4j.properties")));
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

			final Application driver = new Application();
			driver.initialize();

			LOGGER.info("using moltin.aw.home: {}", Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME);

			driver.getAdventureWorksData().dump();

			driver.getMoltinStore().deleteProducts();
			driver.getMoltinStore().deleteCategories();
			driver.getMoltinStore().deleteFiles();
			driver.getMoltinStore().populate(driver.getAdventureWorksData());

		} catch (final Exception e) {
			LOGGER.error("error: {}", ExceptionUtils.getStackTrace(e));
		}
	}

	private AdventureWorksData adventureWorksData;

	private MoltinStore moltinStore;

	private AdventureWorksData getAdventureWorksData() {
		return adventureWorksData;
	}

	private MoltinStore getMoltinStore() {
		return moltinStore;
	}

	public Application initialize() throws ConfigurationException, IOException {

		// do not alter sequence of initializaion
		final Configuration configuration = new Configuration();
		Context.put(Configuration.class, configuration.initialize());

		setMoltinStore(new MoltinStore());
		getMoltinStore().initialize();

		setAdventureWorksData(new AdventureWorksData(Paths.get(configuration.getAdventureWorksDataLocation())));
		getAdventureWorksData().initialize();

		return this;
	}

	private void setAdventureWorksData(AdventureWorksData adventureWorksData) {
		this.adventureWorksData = adventureWorksData;
	}

	private void setMoltinStore(MoltinStore moltinStore) {
		this.moltinStore = moltinStore;
	}

}
