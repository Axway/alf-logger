package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.log.Arguments;

@SuppressWarnings({"unused", "WeakerAccess"})
public class IllegalStateFormattedException extends FormattedRuntimeException {
    public IllegalStateFormattedException(String message) {
        super(message);
    }

    public IllegalStateFormattedException(Throwable cause, String message) {
        super(cause, message);
    }

    public IllegalStateFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public IllegalStateFormattedException(Throwable cause, String message, Consumer<Arguments> args) {
        super(cause, message, args);
    }
}
