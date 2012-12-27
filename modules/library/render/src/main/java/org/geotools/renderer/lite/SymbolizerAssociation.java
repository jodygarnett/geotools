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
package org.geotools.renderer.lite;


import org.geotools.factory.Hints;
import org.geotools.referencing.operation.transform.ConcatenatedTransform;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 * Cache of context information associated with the Symbolizer.
 * <p>
 * Examples of context information include the transformations
 * employed at different stages of the rendering pileline.
 * 
 * @source $URL$
 */
class SymbolizerAssociation{
    /**
     * Full transform from data {@link #crs} through to viewport CRS followed through
     * to the screen.
     */
     public MathTransform  xform = null;
     
     /**
      * Initial transform between data {@link #crs} and viewport CRS.
      */
     public MathTransform  crsxform = null;
     
     /**
      * The source CooridinateReferenceSystem used for the individual Geometries.
      * <p>
      * Although we request Geometry information with {@link Hints#FEATURE_2D} the
      * geometry may still be provided with with 3D ordinates. In this case
      * we will need to post process the information into 2D for rendering.
      */
     public CoordinateReferenceSystem crs = null;
     
     /**
      * Transform used between viewport CRS through to the screen.
      */
     public MathTransform axform;
     
     
     /**
      * The inverse of crsxform, will return null if not available (for example when going between 2D and 3D data).
      * @return Inverse of crsxform, or null if not available
      */
     public MathTransform crsxfromInverse(){
         if (crsxform != null) {
             if (crsxform instanceof ConcatenatedTransform
                     && ((ConcatenatedTransform) crsxform).transform1
                             .getTargetDimensions() >= 3
                     && ((ConcatenatedTransform) crsxform).transform2
                             .getTargetDimensions() == 2) {
                 return null; // We are downcasting 3D data to 2D data so no inverse is available
             } else {
                 try {
                     return crsxform.inverse();
                 } catch (Exception cannotReverse) {
                     return null; // reverse transform not available
                 }
             }
         }
         return null; // not available
     }
     /**
      * The inverse of xform, will return null if not available (for example when going between 2D and 3D data).
      * @return Inverse of xform, or null if not available
      */
     public MathTransform xformInverse(){
         if (xform != null) {
             if (xform instanceof ConcatenatedTransform
                     && ((ConcatenatedTransform) xform).transform1
                             .getTargetDimensions() >= 3
                     && ((ConcatenatedTransform) xform).transform2
                             .getTargetDimensions() == 2) {
                 return null; // We are downcasting 3D data to 2D data so no inverse is available
             } else {
                 try {
                     return xform.inverse();
                 } catch (Exception cannotReverse) {
                     return null; // reverse transform not available
                 }
             }
         }
         return null; // not available
     }
}
