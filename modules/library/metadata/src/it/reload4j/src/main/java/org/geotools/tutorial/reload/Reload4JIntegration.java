/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2022, Open Source Geospatial Foundation (OSGeo)
 *
 *    This file is hereby placed into the Public Domain. This means anyone is
 *    free to do whatever they wish with this file. Use it well and enjoy!
 */
package org.geotools.tutorial.reload;

import java.io.File;
import java.util.logging.Logger;
import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.Logging;
import org.geotools.util.logging.Log4JLoggerFactory;

import org.apache.log4j.PropertyConfigurator;

/**
 * Example illustrating use of Reload4J (providing Log4J 1 API) and startup environment.
 */
public class Reload4JIntegration {
    
    public static final Logger LOGGER = initLogger();
    
    public static void main(String args[]) {
        LOGGER.info("Welcome to Reload4J Integration Example");
        LOGGER.info("Configuration " + lookupConfiguration());
        
        LOGGER.finest("Everything is finest...");
        LOGGER.finer("Everything is finer...");
        LOGGER.fine("Everything is fine...");
        LOGGER.info("Everything is okay.");
        LOGGER.warning("Everything is alarming!");
        LOGGER.severe("Everything is terrible!");
    }
    private static Logger initLogger(){
        GeoTools.init();
         if( Logging.ALL.getLoggerFactory() == Log4JLoggerFactory.getInstance() ){
            System.err.println("Expected GeoTools.init() to configure Log4JLoggerFactory, was "+Logging.ALL.getLoggerFactory());
        }
        return Logging.getLogger(Reload4JIntegration.class);
    }
    
    private static String lookupConfiguration(){
        if( System.getProperties().containsKey("log4j.configuration") ){
            File config = new File(System.getProperty("log4j.configuration"));
            if(config.exists()) {
               return config;
            } else {
               LOGGER.warning("The log4j.configuration="+config+" file does not exist");
            }
        }
        else {
            return "built-in log4j.properties resource used";
        }
    }
}