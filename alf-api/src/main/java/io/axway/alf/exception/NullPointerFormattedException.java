package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.Arguments;

public class NullPointerFormattedException extends FormattedRuntimeException {
    public NullPointerFormattedException() {
    }

    public NullPointerFormattedException(Throwable cause) {
        super(cause);
    }

    public NullPointerFormattedException(String message) {
        super(message);
    }

    public NullPointerFormattedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPointerFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public NullPointerFormattedException(String message, Consumer<Arguments> args, Throwable cause) {
        super(message, args, cause);
    }
}
