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
import org.opengis.filter.BinaryComparisonOperator;


/**
 * Defines a comparison filter (can be a math comparison or generic equals).
 * <p>
 * This filter implements a comparison - of some sort - between two
 * expressions. The comparison may be a math comparison or a generic equals
 * comparison.  If it is a math comparison, only math expressions are allowed;
 * if it is an equals comparison, any expression types are allowed. Note that
 * this comparison does not attempt to restrict its expressions to be
 * meaningful.  This means that it considers itself a valid filter as long as
 * the expression comparison returns a valid result.  It does no checking to
 * see whether or not the expression comparison is meaningful with regard to
 * checking feature attributes.  In other words, this is a valid filter: <b>52
 * = 92</b>, even though it will always return the same result and could be
 * simplified away.  It is up the the filter creator, therefore, to attempt to
 * simplify/make meaningful filter logic.
 *
 * @author Rob Hranac, Vision for New York
 *
 *
 * @source $URL$
 * @version $Id$
 *
 * @deprecated use {@link org.opengis.filter.BinaryComparisonOperator}
 */
public interface CompareFilter extends Filter, BinaryComparisonOperator {

}
