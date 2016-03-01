package org.geotools.gce.imagemosaic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.geotools.coverage.grid.io.GranuleSource;
import org.geotools.coverage.grid.io.GranuleStore;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.data.DefaultTransaction;
import org.geotools.factory.Hints;
import org.geotools.gce.imagemosaic.catalog.GranuleCatalog;
import org.geotools.gce.imagemosaic.catalog.index.Indexer.Collectors.Collector;
import org.geotools.gce.imagemosaic.catalogbuilder.CatalogBuilderConfiguration;
import org.geotools.gce.imagemosaic.properties.PropertiesCollector;
import org.geotools.geometry.GeneralEnvelope;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public interface CatalogManager {

    /**
     * Create a GranuleCatalog on top of the provided configuration
     * @param runConfiguration
     * @return
     * @throws IOException
     */
    GranuleCatalog createCatalog(CatalogBuilderConfiguration runConfiguration)
            throws IOException;

    /**
     * Create or load a GranuleCatalog on top of the provided configuration
     * @param runConfiguration
     * @param create if true create a new catalog, otherwise it is loaded
     * @return
     * @throws IOException
     */
    GranuleCatalog createCatalog(CatalogBuilderConfiguration runConfiguration,
            boolean create) throws IOException;
    
    /**
     * Create a {@link GranuleCatalog} on top of the provided Configuration
     * @param sourceURL
     * @param configuration
     * @param hints
     * @return
     * @throws IOException 
     */
    GranuleCatalog createCatalog(final URL sourceURL, final MosaicConfigurationBean configuration, Hints hints) throws IOException;

    /**
     * Tries to drop a datastore referred by the datastore connections 
     * properties specified in the provided file.
     * 
     * Current implementation only drop a postGIS datastore.
     * 
     * @param datastoreProperties
     * @throws IOException
     */
    void dropDatastore(File datastoreProperties) throws IOException;

    Properties createGranuleCatalogProperties(File datastoreProperties)
            throws IOException;

    GranuleCatalog createGranuleCatalogFromDatastore(File parent,
            File datastoreProperties, boolean create, Hints hints) throws IOException;

    /**
     * Create a granule catalog from a datastore properties file
     */
    GranuleCatalog createGranuleCatalogFromDatastore(File parent,
            File datastoreProperties, boolean create, boolean wraps, Hints hints)
            throws IOException;

    GranuleCatalog createGranuleCatalogFromDatastore(File parent,
            Properties properties, boolean create, boolean wraps, Hints hints) throws IOException;

    /**
     * Create a {@link SimpleFeatureType} from the specified configuration.
     * @param configurationBean
     * @param actualCRS
     * @return
     */
    SimpleFeatureType createSchema(CatalogBuilderConfiguration runConfiguration,
            String name, CoordinateReferenceSystem actualCRS);

    SimpleFeatureType createDefaultSchema(
            CatalogBuilderConfiguration runConfiguration, String name,
            CoordinateReferenceSystem actualCRS);

    /**
     * Get a {@link GranuleSource} related to a specific coverageName from an inputReader
     * and put the related granules into a {@link GranuleStore} related to the same coverageName
     * of the mosaicReader.
     * 
     * @param coverageName the name of the coverage to be managed
     * @param fileBeingProcessed the reference input file
     * @param inputReader the reader source of granules
     * @param mosaicReader the reader where to store source granules
     * @param configuration the configuration
     * @param envelope
     * @param transaction
     * @param propertiesCollectors
     * @throws IOException
     */
    void updateCatalog(
            final String coverageName,
            final File fileBeingProcessed,
            final GridCoverage2DReader inputReader,
            final ImageMosaicReader mosaicReader,
            final CatalogBuilderConfiguration configuration, 
            final GeneralEnvelope envelope,
            final DefaultTransaction transaction, 
            final List<PropertiesCollector> propertiesCollectors) throws IOException;

    List<Collector> customCollectors();

}