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
 * Defines an OpenGIS Filter object, with default behaviors for all methods.
 *
 * @author Rob Hranac, Vision for New York
 *
 *
 * @source $URL$
 * @version $Id$
 *
 * @deprecated use {@link org.opengis.filter.Filter}
 */
public interface Filter extends FilterType, org.opengis.filter.Filter {
    static final org.opengis.filter.Filter ALL = org.opengis.filter.Filter.EXCLUDE;
    static final org.opengis.filter.Filter NONE = org.opengis.filter.Filter.INCLUDE;

    /**
     * Type code for this Filter see FilterType for constants.
     *
     * @return Type code for this filter, this is the same as Filters.getFilterType( filter )
     *
     * @task Gets a short representation of the type of this filter.
     *
     * @deprecated The enumeration base type system is replaced with a class
     * based type system. An 'instanceof' check should be made instead of
     * calling this method.
     */
    short getFilterType();

    /**
     * Used by FilterVisitors to perform some action on this filter instance.
     * Typicaly used by Filter decoders, but may also be used by any thing
     * which needs infomration from filter structure. Implementations should
     * always call: visitor.visit(this); It is importatant that this is not
     * left to a parent class unless the parents API is identical.
     *
     * @param visitor The visitor which requires access to this filter, the
     *        method must call visitor.visit(this);
     *
     * @deprecated use {@link org.opengis.filter.Filter#accept(FilterVisitor, Object)}.
     */
    void accept(FilterVisitor visitor);
}
