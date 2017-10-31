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

import com.microsoft.azure.search.service.AzureSearchService;
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

			final Application application = new Application();
			application.initialize();

			LOGGER.info("moltin.java.adventure.works.home: {}", Util.MOLTIN_JAVA_ADVENTURE_WORKS_HOME);

		//	application.getAdventureWorksData().dump();
		//	application.getMoltinStore().deleteFiles();
		//	application.getMoltinStore().deleteCategories();
		//	application.getMoltinStore().deleteProducts();
		//	application.getMoltinStore().populate(application.getAdventureWorksData());

			final AzureSearchService ass = new AzureSearchService();

			ass.deleteCategoriesIndex();
			ass.deleteProductsIndex();
			ass.deleteVariantsIndex();

			Thread.sleep(5000);

			ass.defineCategoriesIndex();
			ass.defineProductsIndex();
			ass.defineVariantsIndex();
			ass.populateIndexes(application.getMoltinStore());

		} catch (final Exception e) {
			LOGGER.error("error: {}", ExceptionUtils.getStackTrace(e));
		}
	}

	private AdventureWorksData adventureWorksData;

	private MoltinStore moltinStore;

	public AdventureWorksData getAdventureWorksData() {
		return adventureWorksData;
	}

	public MoltinStore getMoltinStore() {
		return moltinStore;
	}

	public Application initialize() throws ConfigurationException, IOException {

		// do not alter sequence of initializaion
		final Configuration configuration = new Configuration();
		Context.put(Configuration.class, configuration.initialize());

		setMoltinStore(new MoltinStore());
		getMoltinStore().initialize();

		//setAdventureWorksData(new AdventureWorksData(Paths.get(configuration.getAdventureWorksDataLocation())));
		//getAdventureWorksData().initialize();

		return this;
	}

	public void setAdventureWorksData(final AdventureWorksData adventureWorksData) {
		this.adventureWorksData = adventureWorksData;
	}

	public void setMoltinStore(final MoltinStore moltinStore) {
		this.moltinStore = moltinStore;
	}

}
