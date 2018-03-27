package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.Arguments;

public class IllegalStateFormattedException extends FormattedRuntimeException {
    public IllegalStateFormattedException() {
    }

    public IllegalStateFormattedException(Throwable cause) {
        super(cause);
    }

    public IllegalStateFormattedException(String message) {
        super(message);
    }

    public IllegalStateFormattedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalStateFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public IllegalStateFormattedException(String message, Consumer<Arguments> args, Throwable cause) {
        super(message, args, cause);
    }
}
