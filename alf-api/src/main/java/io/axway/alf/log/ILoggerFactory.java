package io.axway.alf.log;

/**
 * Logger factory SPI interface
 */
public interface ILoggerFactory {

    /**
     * @param name the name of the logger to be retrieve
     * @return a {@link Logger} with the given {@code name}
     */
    Logger getLogger(String name);

    /**
     * @param clazz the class which name should be used to retrieve the logger
     * @return a {@link Logger} with the given {@code clazz} name
     */
    Logger getLogger(Class<?> clazz);
}
