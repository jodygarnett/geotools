/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2003-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.data.vpf;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.vpf.io.RowFieldTest;
import org.geotools.test.TestData;
import org.geotools.util.KVP;
import org.junit.Test;
import org.opengis.feature.type.Name;

import junit.framework.TestCase;

/**
 * Quick test to confirm DataStore functionality.
 * 
 * @source $URL$
 */
public class VPFDataStoreTest {
    @Test
    public void factory() throws Exception {
        File dht = TestData.file(RowFieldTest.class, "dnc13/dht");
        
        VPFDataStoreFactory factory = new VPFDataStoreFactory();
        
        // directory containing the lht file?
        KVP params = new KVP( "url", dht.getParentFile().toURI().toURL() );
        
        assertTrue( factory.canProcess( params ) );
        
        DataStore vpf = factory.createDataStore( params );
        List<Name> names = vpf.getNames();
        assertFalse( "has content", names.isEmpty() );
        Name name = names.get(0);
        
        SimpleFeatureSource source = vpf.getFeatureSource(name);
        assertTrue( "sample data does not have features", source.getCount(Query.ALL) > 0 );
    }
    @Test
    public void noamerVMAP0() throws Exception{
        URL noamer=new URL("file:///Volumes/Fiore/jody/Downloads/v0noa_5/vmaplv0/noamer/");
        VPFDataStoreFactory factory = new VPFDataStoreFactory();
        
        // directory containing the lht file?
        KVP params = new KVP( "url", noamer );
        
        assertTrue( factory.canProcess( params ) );
        
        DataStore vpf = factory.createDataStore( params );
        List<Name> names = vpf.getNames();
        assertFalse( "has content", names.isEmpty() );
        System.out.println(names);
        
        String name = "ADMINISTRATIVE_BOUNDARY_LINE";

        SimpleFeatureSource source = vpf.getFeatureSource(name);
        assertTrue( "has features", source.getCount(Query.ALL) > 0 );
    }
}
