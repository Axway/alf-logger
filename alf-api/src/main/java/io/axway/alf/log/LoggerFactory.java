package io.axway.alf.log;

import java.util.*;

/**
 * Entry point class to retrieve {@link Logger} objects.<p>
 * Under the hood this class delegate to an {@link ILoggerFactory} implementation that is found thanks to JDK {@code ServiceLoader} mechanism.<p>
 */
public final class LoggerFactory {
    private static final ILoggerFactory DELEGATE;

    static {
        ILoggerFactory spi = null;
        try {
            ServiceLoader<ILoggerFactory> serviceLoader = ServiceLoader.load(ILoggerFactory.class, ILoggerFactory.class.getClassLoader());
            Iterator<ILoggerFactory> it = serviceLoader.iterator();

            if (!it.hasNext()) {
                throw new ExceptionInInitializerError("No io.axway.alf.log.ILoggerFactory implementation is available through ServiceLoader");
            }

            ILoggerFactory spi0 = it.next();

            if (it.hasNext()) {
                throw new ExceptionInInitializerError("More than one io.axway.alf.log.ILoggerFactory implementation is available through ServiceLoader");
            }

            spi = spi0;
        } finally {
            DELEGATE = spi;
        }
    }

    /**
     * @param name the name of the logger to be retrieve
     * @return a {@link Logger} with the given {@code name}
     */
    public static Logger getLogger(String name) {
        return DELEGATE.getLogger(name);
    }

    /**
     * @param clazz the class which name should be used to retrieve the logger
     * @return a {@link Logger} with the given {@code clazz} name
     */
    public static Logger getLogger(Class<?> clazz) {
        return DELEGATE.getLogger(clazz);
    }

    private LoggerFactory() {
        // static method class only
    }
}
