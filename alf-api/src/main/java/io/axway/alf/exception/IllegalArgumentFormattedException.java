package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.Arguments;

public class IllegalArgumentFormattedException extends FormattedRuntimeException {
    public IllegalArgumentFormattedException() {
    }

    public IllegalArgumentFormattedException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentFormattedException(String message) {
        super(message);
    }

    public IllegalArgumentFormattedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public IllegalArgumentFormattedException(String message, Consumer<Arguments> args, Throwable cause) {
        super(message, args, cause);
    }
}
