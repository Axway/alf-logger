package io.axway.alf;

import java.util.function.*;
import javax.annotation.*;

/**
 * Collector of message arguments
 */
public interface Arguments {
    /**
     * Adds an argument
     *
     * @param key argument key
     * @param value argument value
     * @return current argument collector
     */
    Arguments add(String key, @Nullable Object value);

    /**
     * Adds nested arguments
     *
     * @param key argument key
     * @param arguments nested arguments
     * @return current argument collector
     */
    Arguments add(String key, @Nullable Consumer<Arguments> arguments);

    /**
     * Adds a String argument
     *
     * @param key argument key
     * @param value argument value
     * @return current argument collector
     */
    default Arguments add(String key, @Nullable String value) {
        return add(key, (Object) value);
    }

    /**
     * Adds an integer argument
     *
     * @param key argument key
     * @param value argument value
     * @return current argument collector
     */
    default Arguments add(String key, int value) {
        return add(key, (Object) value);
    }

    /**
     * Adds a long argument
     *
     * @param key argument key
     * @param value argument value
     * @return current argument collector
     */
    default Arguments add(String key, long value) {
        return add(key, (Object) value);
    }

    /**
     * Adds a double argument
     *
     * @param key argument key
     * @param value argument value
     * @return current argument collector
     */
    default Arguments add(String key, double value) {
        return add(key, (Object) value);
    }
}
