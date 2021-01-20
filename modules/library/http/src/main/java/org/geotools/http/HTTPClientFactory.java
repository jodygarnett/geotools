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
 *
 */
package org.geotools.http;

import org.geotools.util.factory.Factory;
import org.geotools.util.factory.Hints;

/**
 * Factory class to create a HTTP client. Default implementation is within library gt-http.
 *
 * @author Roar Brænden
 */
public interface HTTPClientFactory extends Factory {

    /**
     * Method used to check if client in Hints can be created
     *
     * @return Client class
     */
    boolean willCreate(Hints hints);

    /**
     * Method called to create the client
     *
     * @return default http client
     */
    HTTPClient createClient();

    /**
     * Called by HTTPFactoryFinder to create client
     *
     * <p>Takes care of any delegation
     *
     * @param hints Merged with system defaults
     * @return
     */
    HTTPClient createClient(Hints hints);
}
