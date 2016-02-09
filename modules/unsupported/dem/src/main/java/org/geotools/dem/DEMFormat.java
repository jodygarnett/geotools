/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2015, Open Source Geospatial Foundation (OSGeo)
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
 *
 */
package org.geotools.dem;

import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.gce.imagemosaic.CatalogManagerImpl;
import org.geotools.gce.imagemosaic.ImageMosaicFormat;
import org.geotools.gce.imagemosaic.ImageMosaicReader;
import org.geotools.gce.imagemosaic.Utils;
import org.geotools.gce.imagemosaic.Utils.Prop;
import org.geotools.gce.imagemosaic.catalog.index.Indexer.Collectors.Collector;
import org.geotools.gce.imagemosaic.catalogbuilder.CatalogBuilderConfiguration;
import org.geotools.parameter.DefaultParameterDescriptorGroup;
import org.geotools.parameter.ParameterGroup;
import org.opengis.coverage.grid.Format;
import org.opengis.coverage.grid.GridCoverageWriter;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.parameter.GeneralParameterDescriptor;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Devon Tucker
 * @author Niels Charlier
 */
public class DEMFormat extends AbstractGridFormat implements Format {

    public DEMFormat() {
        HashMap<String,String> info = new HashMap<String,String> ();
        info.put("name", "Digital Elevation Modal");
        info.put("description", "DEM based on disparate rasters");
        info.put("vendor", "Geotools");
        info.put("docURL", "");
        info.put("version", "1.0");
        this.mInfo = info;
        

        // reading parameters
        readParameters = new ParameterGroup(new DefaultParameterDescriptorGroup(mInfo,
                new GeneralParameterDescriptor[]{
                        READ_GRIDGEOMETRY2D,
                        INPUT_TRANSPARENT_COLOR,
                ImageMosaicFormat.OUTPUT_TRANSPARENT_COLOR,
                USE_JAI_IMAGEREAD,
                ImageMosaicFormat.BACKGROUND_VALUES,
                SUGGESTED_TILE_SIZE,
                ImageMosaicFormat.ALLOW_MULTITHREADING,
                ImageMosaicFormat.MAX_ALLOWED_TILES,
                TIME,
                ELEVATION,
                ImageMosaicFormat.FILTER,
                ImageMosaicFormat.ACCURATE_RESOLUTION,
                ImageMosaicFormat.SORT_BY,
                ImageMosaicFormat.MERGE_BEHAVIOR,
                ImageMosaicFormat.FOOTPRINT_BEHAVIOR
        }));
    }

    @Override public AbstractGridCoverage2DReader getReader(Object source) {
        return getReader(source, null);
    }

    @Override
    public AbstractGridCoverage2DReader getReader(Object source, Hints hints) {
                
        try {                       
            return new ImageMosaicReader(source, hints, new CatalogManagerImpl() {
                
                @Override
                public SimpleFeatureType createDefaultSchema(CatalogBuilderConfiguration runConfiguration, String name,
                        CoordinateReferenceSystem actualCRS) {
                    final SimpleFeatureTypeBuilder featureBuilder = new SimpleFeatureTypeBuilder();
                    featureBuilder.setName(runConfiguration.getParameter(Prop.INDEX_NAME));
                    featureBuilder.setNamespaceURI("http://boundlessgeo.com//");
                    featureBuilder.add(runConfiguration.getParameter(Prop.LOCATION_ATTRIBUTE).trim(), String.class);
                    featureBuilder.add("the_geom", Polygon.class, actualCRS);
                    featureBuilder.setDefaultGeometry("the_geom");
                    addAttributes("date", featureBuilder, Date.class);                    
                    addAttributes("resX", featureBuilder, Double.class);
                    addAttributes("resY", featureBuilder, Double.class);
                    
                    
                    return featureBuilder.buildFeatureType();
                }
                
                @Override
                public List<Collector> customCollectors() {
                    List<Collector> list = new ArrayList<Collector>();
                    
                    Collector collectorDate = Utils.OBJECT_FACTORY.createIndexerCollectorsCollector();
                    collectorDate.setSpi("DateExtractorSPI");
                    collectorDate.setMapped("date");   
                    collectorDate.setValue("");                 
                    list.add(collectorDate);       
                    
                    Collector collectorX = Utils.OBJECT_FACTORY.createIndexerCollectorsCollector();
                    collectorX.setSpi("ResolutionExtractorSPI");
                    collectorX.setMapped("resX");  
                    collectorX.setValue("");
                    list.add(collectorX);
                    
                    Collector collectorY = Utils.OBJECT_FACTORY.createIndexerCollectorsCollector();
                    collectorY.setSpi("ResolutionExtractorSPI");
                    collectorY.setMapped("resY");   
                    collectorY.setValue("");                 
                    list.add(collectorY);       
                    
                    return list;
                }
                
            }); 
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public GridCoverageWriter getWriter(Object destination) {
        return null;
    }

    @Override public boolean accepts(Object source, Hints hints) {
        return true;
    }

    @Override public GeoToolsWriteParams getDefaultImageIOWriteParameters() {
        return null;
    }

    @Override public GridCoverageWriter getWriter(Object destination, Hints hints) {
        return null;
    }
}
