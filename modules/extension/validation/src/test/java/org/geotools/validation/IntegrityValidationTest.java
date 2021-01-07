/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2004-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.validation;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.geotools.data.DataTestCase;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.validation.attributes.UniqueFIDValidation;
import org.junit.After;
import org.junit.Test;

/**
 * IntegrityValidationTest purpose.
 *
 * <p>Description of IntegrityValidationTest ...
 *
 * <p>
 *
 * @author jgarnett, Refractions Research, Inc.
 * @author $Author: sploreg $ (last modification)
 * @version $Id$
 */
public class IntegrityValidationTest extends DataTestCase {
    MemoryDataStore store;

    /** Construct data store for use. */
    public void init() throws Exception {
        super.init();
        store = new MemoryDataStore();
        store.addFeatures(roadFeatures);
        store.addFeatures(riverFeatures);
    }

    @After
    public void tearDown() throws Exception {
        store = null;
        super.tearDown();
    }

    @Test
    public void testUniqueFIDIntegrityValidation() throws Exception {
        // the visitor
        RoadValidationResults validationResults = new RoadValidationResults();

        UniqueFIDValidation validator = new UniqueFIDValidation();
        validator.setName("isValidRoad");
        validator.setDescription("Tests to see if a road is valid");
        validator.setTypeRef("*");
        validationResults.setValidation(validator);

        Map<String, SimpleFeatureSource> layers = new HashMap<>();
        layers.put("road", store.getFeatureSource("road"));
        layers.put("river", store.getFeatureSource("river"));

        assertTrue(
                validator.validate(layers, null, validationResults)); // validate will return true
    }
}
