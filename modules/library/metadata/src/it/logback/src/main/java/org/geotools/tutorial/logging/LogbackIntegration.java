/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2016, Open Source Geospatial Foundation (OSGeo)
 *
 *    This file is hereby placed into the Public Domain. This means anyone is
 *    free to do whatever they wish with this file. Use it well and enjoy!
 */
package org.geotools.tutorial.logging;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.spi.ConfigurationWatchList;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.LogbackLoggerFactory;
import org.geotools.util.logging.Logging;
import org.slf4j.LoggerFactory;

/**
 * Example illustrating use of SLF4J API and Logback startup environment.
 */
public class LogbackIntegration {

    public static void main(String args[]) {
        GeoTools.init();
        // ((LoggerContext)LogManager.getContext(false)).reconfigure();
        if( Logging.ALL.getLoggerFactory() == LogbackLoggerFactory.getInstance() ){
            System.err.println("Expected GeoTools.init() to configure LogbackLoggerFactory, was "+Logging.ALL.getLoggerFactory());
        }

        final Logger LOGGER = Logging.getLogger(LogbackIntegration.class);
        if(!LOGGER.getClass().getName().equals("org.geotools.util.logging.LogbackLogger")){
           LOGGER.severe("LogbackLogger expected, but was:" + LOGGER.getClass().getName() );
        }

        LOGGER.info("Welcome to Logback Integration Example");
        LOGGER.info("Configuration " + lookupConfiguration());

        LOGGER.finest("Everything is finest...");
        LOGGER.finer("Everything is finer...");
        LOGGER.fine("Everything is fine...");
        LOGGER.info("Everything is okay.");
        LOGGER.warning("Everything is alarming!");
        LOGGER.severe("Everything is terrible!");
    }

    private static String lookupConfiguration(){
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            ConfigurationWatchList configurationWatchList = ConfigurationWatchListUtil.getConfigurationWatchList(context);
            return configurationWatchList.getMainURL().toString();
        } catch (Exception unknown) {
            return "unknown";
        }
    }
}