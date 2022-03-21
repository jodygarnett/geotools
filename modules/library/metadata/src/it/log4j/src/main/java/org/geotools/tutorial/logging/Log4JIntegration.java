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
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.NullConfiguration;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.apache.logging.log4j.core.config.properties.PropLog4J2LoggerFactory.javaertiesConfiguration;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.yaml.YamlConfiguration;
import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.Log4J2LoggerFactory;
import org.geotools.util.logging.Logging;
/**
 * Example illustrating use of Log4J 2 API and startup environment.
 */
public class Log4JIntegration {

    public static void main(String args[]) {
        GeoTools.init();
        // ((LoggerContext)LogManager.getContext(false)).reconfigure();
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
            LoggerContext context = (LoggerContext) LogManager.getContext();
            Configuration configuration = context.getConfiguration();
            if(configuration instanceof XmlConfiguration){
                return ((XmlConfiguration)configuration).getName();
            }
            else if(configuration instanceof YamlConfiguration){
                return ((YamlConfiguration)configuration).getName();
            }
            else if(configuration instanceof JsonConfiguration){
                return ((JsonConfiguration)configuration).getName();
            }
            else if(configuration instanceof PropertiesConfiguration){
                return ((PropertiesConfiguration)configuration).getName();
            }
            else if(configuration instanceof DefaultConfiguration){
                return "org.apache.logging.log4j.level="+System.getProperty("org.apache.logging.log4j.level","ERROR");
            }
            else if (configuration instanceof BuiltConfiguration){
                return "built-in";
            }
            else if(configuration instanceof NullConfiguration){
                return "null configuration";
            }
            return null;
        } catch (Exception unknown) {
            return "unknown";
        }
    }
}