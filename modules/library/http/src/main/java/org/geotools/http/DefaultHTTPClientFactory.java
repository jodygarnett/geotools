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

import java.util.Collections;
import java.util.List;
import org.geotools.util.factory.Hints;

/**
 * Default factory for generating HTTP client's. Will deliver a SimpleHttpClient.
 *
 * @author Roar Brænden
 */
public class DefaultHTTPClientFactory extends AbstractHTTPClientFactory {

    public DefaultHTTPClientFactory(){
    }

    public DefaultHTTPClientFactory(Hints hints){
        super(hints);
    }

    @Override
    protected List<Class<?>> clientClasses() {
        return Collections.singletonList(SimpleHttpClient.class);
    }

    @Override
    public HTTPClient createClient() {
        return applyLoggingDefault(new SimpleHttpClient());
    }

    @Override
    public HTTPClient createClient(Hints hints) {
        return applyLogging(new SimpleHttpClient(), hints);
    }
}
