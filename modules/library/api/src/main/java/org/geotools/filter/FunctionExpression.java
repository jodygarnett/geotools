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

import java.util.List;

import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;
import org.geotools.factory.Factory;

/**
 * Interface for a function expression implementation
 *
 * @author James Macgill, PSU
 *
 *
 * @source $URL$
 *
 * @deprecated use {@link org.opengis.filter.expression.Function}
 */
public interface FunctionExpression extends Expression, Factory, Function {
    /**
     *  The number of arguments this <Function> requires.
     *
     *  For example &lt;Function name="strCat"&gt; [arg1][arg2]&lt;/Function&gt;.
     *  This function must have EXACTLY 2 arguments, so this function
     *  would return 2.
     *  <p>
     *  The parser might use this information to ensure validity,
     *  and its also for reporting <Function> capabilities. Users interfaces also
     *  use this information to prompt users for an appropriate number of parameters.
     *  <p>
     *  Update: This same information is available from FunctionName getArgumentCount()
     *  with the following description:
     * <ul>
     * <li>Use a positive number to indicate the number of arguments.
     *     Example: <code>add( number1, number2 ) = 2 </code></li>
     * <li>Use a negative number to indicate a minimum number:
     *    Example:  <code>concat( str1, str2,... ) has -2 </code></li>
     * </ul> 
     * FunctionName provides is part of the Filter 2.0 specification and provides
     * argument name information - in addition to this simple cunt.
     *
     * @return the number of arguments required, same as FunctionName getArgumentCount()
     */
    int getArgCount();
    
    /**
     * Fallback value to use in the event the function is unavailable in the requested environment.
     * <p>
     * The fallback value is not provided as one of the arguments, as it is an advanced option used
     * in style layer descriptor documents to facilitate interoperability. It allows a user to specify
     * an SQL function, and provide a value to use when the documented is used with a WFS that does
     * not support the provided function.
     * 
     * @param parameters
     */
    void setFallbackValue(Literal fallback);

    /**
     * Sets the Parameters for the function.
     */
    void setParameters(List<org.opengis.filter.expression.Expression> parameters);
}
