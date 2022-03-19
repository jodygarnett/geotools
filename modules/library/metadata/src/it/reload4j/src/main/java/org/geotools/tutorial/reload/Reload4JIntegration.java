/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2016, Open Source Geospatial Foundation (OSGeo)
 *
 *    This file is hereby placed into the Public Domain. This means anyone is
 *    free to do whatever they wish with this file. Use it well and enjoy!
 */
package org.geotools.tutorial.reload;

import java.io.File;
import java.util.logging.Logger;
import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.Logging;

import org.apache.log4j.PropertyConfigurator;

public class Reload4JIntegration {

    public static void main(String args[]) {
        GeoTools.init();
        final Logger LOGGER = Logging.getLogger(Reload4JIntegration.class);

        LOGGER.info("Welcome to Reload4J Integration Example");
        if( System.getProperties().containsKey("log4j.configuration") ){
            File config = new File(System.getProperty("log4j.configuration"));
            if(config.exists()) {
               LOGGER.config("log4j.configuration="+config);
            } else {
                LOGGER.warning("The log4j.configuration="+config+" file proivded does not exist");
            }
        }
        else {
            LOGGER.config("Built-in log4j.properties resource used.");
        }
        LOGGER.finest("Everything is finest...");
        LOGGER.finer("Everything is finer...");
        LOGGER.fine("Everything is fine...");
        LOGGER.info("Everything is okay.");
        LOGGER.warning("Everything is alarming!");
        LOGGER.severe("Everything is terrible!");
    }
}