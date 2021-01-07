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
package org.geotools.gml3.bindings;

import static org.junit.Assert.assertEquals;

import org.geotools.gml3.GML;
import org.geotools.gml3.GML3TestSupport;
import org.junit.Test;
import org.w3c.dom.Document;

public class CurveTypeBindingTest extends GML3TestSupport {

    @Override
    protected boolean enableExtendedArcSurfaceSupport() {
        return true;
    }

    @Test
    public void testEncode() throws Exception {
        Document dom = encode(GML3MockData.compoundCurve(), GML._Curve);
        // print(dom);
        assertEquals(1, dom.getElementsByTagName("gml:ArcString").getLength());
        assertEquals(1, dom.getElementsByTagName("gml:LineStringSegment").getLength());
    }
}
