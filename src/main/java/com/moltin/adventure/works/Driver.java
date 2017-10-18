package com.moltin.adventure.works;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.LogManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.moltin.ws.AuthenticateRequest;

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

		} catch (final Exception e) {
			LOGGER.error("error: {}", ExceptionUtils.getStackTrace(e));
		}

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
