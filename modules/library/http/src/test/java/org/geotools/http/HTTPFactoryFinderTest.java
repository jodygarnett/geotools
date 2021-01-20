/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2020, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.http;

import static org.junit.Assert.assertTrue;

import org.geotools.http.CustomHTTPClientFactory.CustomHttpClient;
import org.geotools.util.factory.Hints;
import org.junit.Before;
import org.junit.Test;

/** @author Roar Brænden */
public class HTTPFactoryFinderTest {

    @Before
    public void resetFactoryRegistry() {
        HTTPFactoryFinder.reset();
    }

    @Test
    public void findingSimpleHttpClientAsDefault() throws Exception {
        HTTPClient client = HTTPFactoryFinder.getClient();

        assertTrue(client != null);
        assertTrue(client instanceof SimpleHttpClient);
    }

    @Test
    public void findDefaultFactory() throws Exception {
        HTTPClientFactory factory = HTTPFactoryFinder.getFactory(null);
        assertTrue(factory != null);
        assertTrue(factory.getImplementationHints().isEmpty());

        HTTPClient client = factory.createClient();
        assertTrue(client != null);
        assertTrue(client instanceof SimpleHttpClient);
    }

    @Test
    public void findDefaultFactoryLogging() throws Exception {
        Hints hints = new Hints(Hints.HTTP_LOGGING, "TRUE");
        HTTPClientFactory factory = HTTPFactoryFinder.getFactory(hints);
        assertTrue("factory logging found", factory != null);
        assertTrue(
                "logging implementation hint",
                factory.getImplementationHints().containsKey(Hints.HTTP_LOGGING));

        HTTPClient client = factory.createClient();
        assertTrue(client != null);
        assertTrue(client instanceof LoggingHTTPClient);
    }

    @Test
    public void findingCustomHttpClientTestByHints() throws Exception {

        HTTPClient client =
                HTTPFactoryFinder.getClient(new Hints(Hints.HTTP_CLIENT, CustomHttpClient.class));

        assertTrue(client instanceof CustomHttpClient);
    }

    @Test
    public void usingSystemPropertiesToSetLogging() throws Exception {

        Hints.putSystemDefault(Hints.HTTP_LOGGING, "True");
        try {
            HTTPClient client = HTTPFactoryFinder.getClient();
            assertTrue(client instanceof LoggingHTTPClient);
        } finally {
            Hints.removeSystemDefault(Hints.HTTP_LOGGING);
        }
    }

    @Test
    public void avoidLoggingInspiteSystemProperty() throws Exception {
        Hints.putSystemDefault(Hints.HTTP_LOGGING, "True");
        try {
            HTTPClient client = HTTPFactoryFinder.getClient(new Hints(Hints.HTTP_LOGGING, "False"));
            assertTrue(client instanceof SimpleHttpClient);
        } finally {
            Hints.removeSystemDefault(Hints.HTTP_LOGGING);
        }
    }

    @Test
    public void usingSystemPropertiesToSetLoggingWithCharset() throws Exception {

        Hints.putSystemDefault(Hints.HTTP_LOGGING, "utf-8");
        try {
            HTTPClient client = HTTPFactoryFinder.getClient();
            assertTrue(client instanceof LoggingHTTPClient);
        } finally {
            Hints.removeSystemDefault(Hints.HTTP_LOGGING);
        }
    }
}
