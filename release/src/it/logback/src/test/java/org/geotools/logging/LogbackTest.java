/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2007-2022, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.util.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 * Tests the {@link Logging} class configured with {@code LogbackLoggerFactory}.
 */
public class LogbackTest {

    /** Tests the redirection to Logback. */
    @Test
    public void testLogback() throws ClassNotFoundException {
        try {
            Logging.GEOTOOLS.setLoggerFactory("org.geotools.util.logging.LogbackLoggerFactory");
            Logger logger = Logging.getLogger("org.geotools");
            assertTrue(logger instanceof LogbackLogger);
            /*
             * Tests level setting, ending with OFF in order to avoid
             * polluting the standard output stream with this test.
             */
            final Level oldLevel = logger.getLevel();

            logger.setLevel(Level.WARNING);
            assertSame("java level mapped", Level.WARNING, logger.getLevel());
            assertTrue(logger.isLoggable(Level.WARNING));
            assertTrue(logger.isLoggable(Level.SEVERE));
            assertFalse(logger.isLoggable(Level.CONFIG));

            logger.setLevel(Level.CONFIG);
            assertSame("java level mapped", Level.CONFIG, logger.getLevel());
            assertTrue(logger.isLoggable(Level.INFO));
            assertTrue(logger.isLoggable(Level.CONFIG));
            assertFalse(logger.isLoggable(Level.FINE));

            logger.setLevel(Level.FINE);
            assertSame(Level.FINE, logger.getLevel());
            assertFalse(logger.isLoggable(Level.FINER));
            assertTrue(logger.isLoggable(Level.FINE));
            assertTrue(logger.isLoggable(Level.SEVERE));

            logger.setLevel(Level.OFF);
            assertSame("java level mapped", Level.OFF, logger.getLevel());

            logger.finer("Message to Log4J at FINER level.");
            logger.fine("Message to Log4J at FINE level.");
            logger.config("Message to Log4J at CONFIG level.");
            logger.info("Message to Log4J at INFO level.");
            logger.warning("Message to Log4J at WARNING level.");
            logger.severe("Message to Log4J at SEVERE level.");
            logger.setLevel(oldLevel);
        } finally {
            Logging.GEOTOOLS.setLoggerFactory((String) null);
            assertEquals(Logger.class, Logging.getLogger("org.geotools").getClass());
        }
    }
}
