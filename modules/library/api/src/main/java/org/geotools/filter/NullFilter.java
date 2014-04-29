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

import org.opengis.filter.PropertyIsNull;


/**
 * Defines a null filter, which checks to see if an attribute is null.
 *
 * @author Rob Hranac, Vision for New York
 * @author Chris Holmes, TOPP
 *
 *
 * @source $URL$
 * @version $Id$
 *
 * @deprecated use {@link org.opengis.filter.PropertyIsNull}
 */
public interface NullFilter extends Filter, PropertyIsNull {
    
}
