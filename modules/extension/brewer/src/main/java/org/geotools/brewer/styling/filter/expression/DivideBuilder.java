/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2009 - 2011, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.brewer.styling.filter.expression;

import org.geotools.api.filter.FilterFactory;
import org.geotools.api.filter.expression.Divide;
import org.geotools.brewer.styling.builder.Builder;
import org.geotools.factory.CommonFactoryFinder;

public class DivideBuilder implements Builder<Divide> {

    protected FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

    boolean unset = false;

    ChildExpressionBuilder<DivideBuilder> expr1;

    ChildExpressionBuilder<DivideBuilder> expr2;

    public DivideBuilder() {
        reset();
    }

    public DivideBuilder(Divide expression) {
        reset(expression);
    }

    @Override
    public DivideBuilder reset() {
        unset = false;
        expr1 = new ChildExpressionBuilder<>(this);
        expr2 = new ChildExpressionBuilder<>(this);
        return this;
    }

    @Override
    public DivideBuilder reset(Divide original) {
        unset = false;
        expr1 = new ChildExpressionBuilder<>(this, original.getExpression1());
        expr2 = new ChildExpressionBuilder<>(this, original.getExpression2());
        return this;
    }

    @Override
    public DivideBuilder unset() {
        unset = true;
        expr1 = new ChildExpressionBuilder<>(this).unset();
        expr2 = null;
        return this;
    }

    @Override
    public Divide build() {
        if (unset) {
            return null;
        }
        return ff.divide(expr1.build(), expr2.build());
    }

    public ChildExpressionBuilder<DivideBuilder> expr1() {
        return expr1;
    }

    public DivideBuilder expr1(Object literal) {
        expr1.literal(literal);
        return this;
    }

    public ChildExpressionBuilder<DivideBuilder> expr2() {
        return expr2;
    }

    public DivideBuilder expr2(Object literal) {
        expr2.literal(literal);
        return this;
    }
}
