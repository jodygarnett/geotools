/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2021, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.http.commons;

import java.util.Collections;
import java.util.List;
import org.geotools.http.AbstractHTTPClientFactory;
import org.geotools.http.HTTPClient;
import org.geotools.http.SimpleHttpClient;
import org.geotools.util.factory.Hints;

/**
 * Factory for MultithreadedHttpClient
 *
 * <p>To use client set Hints.HTTP_CLIENT_FACTORY=MultithreadedHttpClientFactory.class, or
 * Hints.HTTP_CLIENT=MultithreadedHttpClient.class
 *
 * @author Roar Brænden
 */
public class MultithreadedHttpClientFactory extends AbstractHTTPClientFactory {

    @Override
    public List<Class<?>> clientClasses() {
        return Collections.singletonList(MultithreadedHttpClient.class);
    }

    @Override
    public HTTPClient createClient() {
        return applyLoggingDefault( new MultithreadedHttpClient() );
    }

    @Override
    public HTTPClient createClient(Hints hints) {
        return applyLogging(new MultithreadedHttpClient(), hints);
    }
}
