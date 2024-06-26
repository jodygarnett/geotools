/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2005, Open Geospatial Consortium Inc.
 *
 *    All Rights Reserved. http://www.opengis.org/legal/
 */
package org.geotools.filter.temporal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.geotools.api.temporal.Instant;
import org.geotools.api.temporal.Period;
import org.geotools.api.temporal.TemporalPrimitive;
import org.junit.Test;

public class OverlappedByTest extends TemporalFilterTestSupport {

    @Test
    public void test() throws Exception {
        Instant i1 = instant("2001-07-04T12:08:00.000-0700");
        Instant i2 = instant("2001-07-05T12:08:00.000-0700");

        doAssert(i1, i2, false);
        doAssert(i1, i1, false);

        Period p1 = period("2001-07-04T12:07:00.000-0700", "2001-07-05T12:08:00.000-0700");
        Period p3 = period("2001-07-05T12:07:00.000-0700", "2001-07-05T12:09:00.000-0700");

        doAssert(p1, i1, false);
        doAssert(i1, p1, false);
        doAssert(i1, p3, false);
        doAssert(p3, i1, false);

        doAssert(p1, p3, false);
        doAssert(p3, p1, true);

        doAssert(p1, period("2001-07-04T12:06:59.000-0700", "2001-07-05T12:08:00.000-0700"), false);
        doAssert(p1, period("2001-07-04T12:06:59.000-0700", "2001-07-05T12:07:59.000-0700"), true);
    }

    void doAssert(TemporalPrimitive tp1, TemporalPrimitive tp2, boolean b) {
        OverlappedByImpl a = new OverlappedByImpl(ff.literal(tp1), ff.literal(tp2));
        assertEquals(b, a.evaluate(null));
        assertFalse(a.toString().contains(OverlappedByImpl.class.getName()));
        assertTrue(a.toString().contains(OverlappedByImpl.NAME));
    }
}
