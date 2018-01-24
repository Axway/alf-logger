package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.Arguments;

public class IllegalArgumentFormattedException extends FormattedRuntimeException {
    public IllegalArgumentFormattedException(String message) {
        super(message);
    }

    public IllegalArgumentFormattedException(Throwable cause, String message) {
        super(cause, message);
    }

    public IllegalArgumentFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public IllegalArgumentFormattedException(Throwable cause, String message, Consumer<Arguments> args) {
        super(cause, message, args);
    }
}
