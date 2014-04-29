/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.filter;

import org.opengis.feature.simple.SimpleFeature;


/**
 * Represents a reference to an environmental variable.
 * <p>
 * The environmental variables are expected to be "understood" by the service making
 * use of the Expression. As an example when evaluating an SLD file you can often
 * refer to an environmental variable "DPI", for details review the SLD specification.
 *
 * @author James
 *
 *
 * @source $URL$
 * @deprecated Please use the environmental variable function
 */
public interface EnvironmentVariable extends Expression {

}
