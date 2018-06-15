package io.axway.alf;

import java.util.function.*;
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
     * Add nested arguments
     *
     * @param key argument key
     * @param arguments Nested arguments
     * @return Current argument collector
     */
    Arguments add(String key, @Nullable Consumer<Arguments> arguments);
}
