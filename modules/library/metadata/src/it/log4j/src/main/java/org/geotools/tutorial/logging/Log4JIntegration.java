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

import java.util.logging.Logger;

import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.Log4J2LoggerFactory;
import org.geotools.util.logging.Logging;
/**
 * Example illustrating use of Log4J 2 API and startup environment.
 */
public class Log4JIntegration {

    public static void main(String args[]) {
        GeoTools.init();
        if( Logging.ALL.getLoggerFactory() == Log4J2LoggerFactory.getInstance() ){
            System.err.println("Expected GeoTools.init() to configure Log4J2LoggerFactory, was "+Logging.ALL.getLoggerFactory());
        }

        final Logger LOGGER = Logging.getLogger(Log4JIntegration.class);
        if(!LOGGER.getClass().getName().equals("org.geotools.util.logging.Log4J2Logger")){
           LOGGER.severe("Log4J2Logger expected, but was:" + LOGGER.getClass().getName() );
        }

        LOGGER.info("Welcome to Log4j Integration Example");
        if( System.getProperties().containsKey("log4j2.configurationFile") ){
            LOGGER.config("log4j2.configurationFile="+System.getProperty("log4j2.configurationFile"));
        }
        LOGGER.info("Configuration " + Log4J2LoggerFactory.getInstance().lookupConfiguration());

        LOGGER.finest("Everything is finest...");
        LOGGER.finer("Everything is finer...");
        LOGGER.fine("Everything is fine...");
        LOGGER.config("Everything is configured...");
        LOGGER.info("Everything is okay.");
        LOGGER.warning("Everything is alarming!");
        LOGGER.severe("Everything is terrible!");
    }

}