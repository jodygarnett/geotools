/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2020, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.http;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.geotools.util.factory.Factory;
import org.geotools.util.factory.Hints;
import org.geotools.util.logging.Logging;

/**
 * Base HTTPClientFactory adding wrapper client's to the desired HTTP Client like for instance
 * LoggingHTTPClient.
 *
 * @author Roar Brænden
 */
public abstract class AbstractHTTPClientFactory implements HTTPClientFactory {

    private final Logger LOGGER = Logging.getLogger(AbstractHTTPClientFactory.class);

    /**
     * The {@linkplain Factory#getImplementationHints implementation hints}.
     *
     * <p>This map should be filled by subclasses at construction time, selecting from the provided
     * hints those relevant to the factory use.
     *
     * <p>The ServiceRegistry uses this information to recycle factories that have been configured
     * in the same fashion based on {@link Factory#getImplementationHints()} api contract.
     */
    protected final Map<RenderingHints.Key, Object> configuration = new LinkedHashMap<>();

    /** No-argument constructor used for {@link java.util.ServiceLoader} discovery */
    public AbstractHTTPClientFactory() {}

    /**
     * FactoryRegistery entry point, configuration is provided has Hints.
     *
     * <p>Facotry is expected to review the provided hints, and report any configuration used by the
     * implementation using {@link #getImplementationHints()}.
     *
     * @param hints
     */
    public AbstractHTTPClientFactory(Hints hints) {
        if (hints.containsKey(Hints.HTTP_CLIENT)) {
            Object clientImplementation = hints.get(Hints.HTTP_CLIENT);
            if (clientImplementation instanceof String) {
                String implementationName = (String) clientImplementation;
                boolean match =
                        clientClasses()
                                .stream()
                                .anyMatch((cls) -> cls.getName().equals(implementationName));
                if (match) {
                    configuration.put(Hints.HTTP_CLIENT, clientImplementation);
                }
            } else if (clientImplementation instanceof Class<?>) {
                Class<?> implementationType = (Class<?>) clientImplementation;
                boolean match =
                        clientClasses().stream().anyMatch((cls) -> cls == clientImplementation);
                if (match) {
                    configuration.put(Hints.HTTP_CLIENT, clientImplementation);
                }
            }
        }
        if (hints.containsKey(Hints.HTTP_LOGGING)) {
            Object loggingHint = hints.get(Hints.HTTP_LOGGING);
            if (Hints.HTTP_LOGGING.isCompatibleValue(loggingHint)) {
                configuration.put(Hints.HTTP_CLIENT, loggingHint);
            }
        }
    }

    /** Returns the implementation hints accepted by constructor. */
    public Map<RenderingHints.Key, ?> getImplementationHints() {
        return Collections.unmodifiableMap(configuration);
    }

    @Override
    public boolean willCreate(Hints hints) {
        if (!hints.containsKey(Hints.HTTP_CLIENT)) {
            return true;
        }

        Object val = hints.get(Hints.HTTP_CLIENT);
        if (val instanceof String) {
            final String clsName = (String) val;
            return clientClasses().stream().anyMatch((cls) -> cls.getName().equals(clsName));
        } else {
            final Class<?> cls = (Class<?>) val;
            return clientClasses().stream().anyMatch((cls2) -> cls2 == cls);
        }
    }

    /**
     * List of http client implementations available form this factory.
     *
     * @return http client implementations
     */
    protected abstract List<Class<?>> clientClasses();

    /** Create HTTPClient using the hints used for factory creation. */
    public abstract HTTPClient createClient();

    /**
     * Wraps client with a LoggingHTTPClient if the HTTP_LOGGING hint has been provided during
     * factory setup. Override if using a different LoggingHTTPClient.
     *
     * @param client
     * @return client, possibly wrapped for logging
     */
    protected HTTPClient applyLoggingDefault(HTTPClient client) {
        if (configuration.containsKey(Hints.HTTP_LOGGING)) {
            LOGGER.fine("Applying logging to HTTP Client.");
            final String setting = (String) configuration.get(Hints.HTTP_LOGGING);
            final Boolean logging = Boolean.parseBoolean(setting);
            if (logging) {
                return new LoggingHTTPClient(client);
            } else if (!"false".equalsIgnoreCase(setting)) {
                return new LoggingHTTPClient(client, setting);
            }
        }
        return client;
    }

    @Override
    public HTTPClient createClient(Hints hints) {
        return applyLogging(createClient(), hints);
    }

    /**
     * Wraps client with a LoggingHTTPClient if the HTTP_LOGGING hint is set. Override if using a
     * different LoggingHTTPClient.
     *
     * @param client
     * @param hints
     * @return client, possibly wrapped for logging
     */
    protected HTTPClient applyLogging(HTTPClient client, Hints hints) {
        if (hints.containsKey(Hints.HTTP_LOGGING)) {
            LOGGER.fine("Applying logging to HTTP Client.");
            final String hint = (String) hints.get(Hints.HTTP_LOGGING);
            final Boolean logging = Boolean.parseBoolean(hint);
            if (logging) {
                return new LoggingHTTPClient(client);
            } else if (!"false".equalsIgnoreCase(hint)) {
                return new LoggingHTTPClient(client, hint);
            }
        }
        return client;
    }
}
