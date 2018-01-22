package io.axway.alf.log;

import javax.annotation.*;

/**
 * Collector of message arguments
 */
public interface Arguments {
    /**
     * Add a arguments
     *
     * @param key argument key
     * @param value argument value
     * @return Current argument collector
     */
    Arguments add(String key, @Nullable Object value);

    /**
     * Add a arguments
     *
     * @param key argument key
     * @param value argument value
     * @return Current argument collector
     */
    Arguments add(String key, @Nullable String value);

    /**
     * Add a arguments
     *
     * @param key argument key
     * @param value argument value
     * @return Current argument collector
     */
    Arguments add(String key, long value);
}
