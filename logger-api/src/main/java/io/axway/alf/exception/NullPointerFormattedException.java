package io.axway.alf.exception;

import java.util.function.*;
import io.axway.alf.log.Arguments;

public class NullPointerFormattedException extends FormattedRuntimeException {
    public NullPointerFormattedException(String message) {
        super(message);
    }

    public NullPointerFormattedException(Throwable cause, String message) {
        super(cause, message);
    }

    public NullPointerFormattedException(String message, Consumer<Arguments> args) {
        super(message, args);
    }

    public NullPointerFormattedException(Throwable cause, String message, Consumer<Arguments> args) {
        super(cause, message, args);
    }
}
