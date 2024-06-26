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

package org.geotools.data.gen;

import java.io.IOException;
import java.util.List;
import org.geotools.api.data.FeatureReader;
import org.geotools.api.data.Query;
import org.geotools.api.data.ServiceInfo;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.data.Transaction;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.feature.type.Name;
import org.geotools.data.DataUtilities;
import org.geotools.data.gen.info.GeneralizationInfos;
import org.geotools.data.gen.info.GeneralizationInfosProvider;
import org.geotools.data.gen.info.GeneralizationInfosProviderImpl;
import org.geotools.feature.NameImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PreGeneralizedDataStoreTest {

    @Before
    public void setUp() throws Exception {
        TestSetup.initialize();
    }

    @Test
    public void testBaseFunctionallity() {

        GeneralizationInfosProvider provider = new GeneralizationInfosProviderImpl();
        GeneralizationInfos ginfos = null;
        try {
            ginfos = provider.getGeneralizationInfos("src/test/resources/geninfo_only_base.xml");
            PreGeneralizedDataStore ds = new PreGeneralizedDataStore(ginfos, TestSetup.REPOSITORY);

            String typeName = ds.getTypeNames()[0];
            Assert.assertEquals("GenStreams", typeName);
            Query query = new Query(typeName);

            try (FeatureReader<SimpleFeatureType, SimpleFeature> reader =
                    ds.getFeatureReader(query, Transaction.AUTO_COMMIT)) {
                Assert.assertNotNull(reader);
            }

            SimpleFeatureSource fsource = ds.getFeatureSource(typeName);
            Assert.assertNotNull(fsource);

            fsource = ds.getFeatureSource(new NameImpl(typeName));
            Assert.assertNotNull(fsource);

            ServiceInfo si = ds.getInfo();
            Assert.assertNotNull(si);
            // System.out.println(si);

            List<Name> names = ds.getNames();
            Assert.assertTrue(names.contains(new NameImpl(typeName)));
            Assert.assertEquals("GenStreams", ds.getNames().get(0).getLocalPart());

            fsource = DataUtilities.createView(ds, query);
            Assert.assertNotNull(fsource);

            Assert.assertNotNull(ds.getSchema(typeName));
            Assert.assertNotNull(ds.getSchema(new NameImpl(typeName)));

            ds.dispose();
        } catch (Exception e) {
            java.util.logging.Logger.getGlobal().log(java.util.logging.Level.INFO, "", e);
            Assert.fail();
        }
    }

    @Test
    public void testNotSupportedFeatures() {

        GeneralizationInfosProvider provider = new GeneralizationInfosProviderImpl();
        GeneralizationInfos ginfos = null;
        PreGeneralizedDataStore ds = null;
        String typeName = null;
        try {
            ginfos = provider.getGeneralizationInfos("src/test/resources/geninfo_only_base.xml");
            ds = new PreGeneralizedDataStore(ginfos, TestSetup.REPOSITORY);
            typeName = ds.getTypeNames()[0];
        } catch (IOException ex) {
            java.util.logging.Logger.getGlobal().log(java.util.logging.Level.INFO, "", ex);
            Assert.fail();
        }

        boolean error = true;
        try {
            ds.getFeatureWriter(typeName, Transaction.AUTO_COMMIT);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.getFeatureWriter(typeName, null, Transaction.AUTO_COMMIT);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.getFeatureWriterAppend(typeName, Transaction.AUTO_COMMIT);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.getLockingManager();
        } catch (UnsupportedOperationException ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.updateSchema("GenFeatures", null);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.updateSchema(new NameImpl("GenFeatures"), null);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }

        error = true;
        try {
            ds.createSchema(null);
        } catch (Throwable ex) {
            error = false;
        }
        if (error) {
            Assert.fail();
        }
    }
}
