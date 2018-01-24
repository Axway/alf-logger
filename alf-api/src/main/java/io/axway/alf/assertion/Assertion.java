package io.axway.alf.assertion;

import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;
import io.axway.alf.exception.IllegalArgumentFormattedException;
import io.axway.alf.exception.IllegalStateFormattedException;
import io.axway.alf.exception.NullPointerFormattedException;

public final class Assertion {
    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentFormattedException(message);
        }
    }

    public static void checkArgument(boolean expression, String message, Consumer<Arguments> arguments) {
        if (!expression) {
            throw new IllegalArgumentFormattedException(message, arguments);
        }
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateFormattedException(message);
        }
    }

    public static void checkState(boolean expression, String message, Consumer<Arguments> arguments) {
        if (!expression) {
            throw new IllegalStateFormattedException(message, arguments);
        }
    }

    public static void checkNotNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new NullPointerFormattedException(message);
        }
    }

    public static void checkNotNull(@Nullable Object object, String message, Consumer<Arguments> arguments) {
        if (object == null) {
            throw new NullPointerFormattedException(message, arguments);
        }
    }

    public static void checkNotNullNorEmpty(@Nullable String string, String message) {
        if (string == null) {
            throw new NullPointerFormattedException(message);
        } else if (string.isEmpty()) {
            throw new IllegalStateFormattedException(message);
        }
    }

    public static void checkNotNullNorEmpty(@Nullable String string, String message, Consumer<Arguments> arguments) {
        if (string == null) {
            throw new NullPointerFormattedException(message, arguments);
        } else if (string.isEmpty()) {
            throw new IllegalStateFormattedException(message, arguments);
        }
    }

    public static void checkNotNullNorEmpty(@Nullable Collection<?> collection, String message) {
        if (collection == null) {
            throw new NullPointerFormattedException(message);
        } else if (collection.isEmpty()) {
            throw new IllegalStateFormattedException(message);
        }
    }

    public static void checkNotNullNorEmpty(@Nullable Collection<?> collection, String message, Consumer<Arguments> arguments) {
        if (collection == null) {
            throw new NullPointerFormattedException(message, arguments);
        } else if (collection.isEmpty()) {
            throw new IllegalStateFormattedException(message, arguments);
        }
    }

    private Assertion() {
        // Prevent instantiation
    }
}
