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
package org.geootools.mrdem;

import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.factory.Hints;
import org.geotools.gce.imagemosaic.ImageMosaicReader;
import org.opengis.coverage.grid.Format;
import org.opengis.coverage.grid.GridCoverageWriter;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * Created by devon on 12/10/15.
 */
public class MultiResolutionDEMFormat extends AbstractGridFormat implements Format {

    public MultiResolutionDEMFormat() {
        HashMap<String,String> info = new HashMap<String,String> ();
        info.put("name", "Multi-Resolution Digital Elevation Modal");
        info.put("description", "DEM based on disparate rasters");
        info.put("vendor", "Geotools");
        info.put("docURL", "");
        info.put("version", "1.0");
        this.mInfo = info;
    }

    @Override public AbstractGridCoverage2DReader getReader(Object source) {
        return getReader(source, null);
    }

    @Override public AbstractGridCoverage2DReader getReader(Object source, Hints hints) {
        try {
            return new ImageMosaicReader(source, hints);
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
