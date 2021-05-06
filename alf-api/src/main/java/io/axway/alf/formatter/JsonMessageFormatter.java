package io.axway.alf.formatter;

import java.util.function.*;
import io.axway.alf.Arguments;
import io.axway.alf.exception.ExceptionWithArguments;

public final class JsonMessageFormatter {
    private static final int DEFAULT_BUFFER_SIZE = 64;

    private static final JsonMessageFormatter INSTANCE = new JsonMessageFormatter();
    private static final char MESSAGE_SEPARATOR = ' ';

    public static JsonMessageFormatter getFormatter() {
        return INSTANCE;
    }

    public String formatException(ExceptionWithArguments e) {
        String message = e.getRawMessage();
        if (message != null) {
            Consumer<Arguments> argsConsumer = e.getArguments();
            if (argsConsumer != null) {
                return format(message, argsConsumer);
            } else {
                return format(message);
            }
        } else {
            return null;
        }
    }

    public String format(String message) {
        return message;
    }

    public String format(String message, Throwable throwable) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);
        formatTo(sb, message, throwable);
        return sb.toString();
    }

    public void formatTo(StringBuilder buffer, String message, Throwable throwable) {
        buffer.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(buffer).add("exception", throwable).end();
    }

    public String format(String message, Consumer<Arguments> arguments) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);
        formatTo(sb, message, arguments);
        return sb.toString();
    }

    public void formatTo(StringBuilder buffer, String message, Consumer<Arguments> arguments) {
        buffer.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(buffer).add("args", arguments).end();
    }

    public String format(String message, Consumer<Arguments> arguments, Throwable throwable) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);
        formatTo(sb, message, arguments, throwable);
        return sb.toString();
    }

    public void formatTo(StringBuilder buffer, String message, Consumer<Arguments> arguments, Throwable throwable) {
        buffer.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(buffer).add("args", arguments).add("exception", throwable).end();
    }

    private JsonMessageFormatter() {
    }
}
