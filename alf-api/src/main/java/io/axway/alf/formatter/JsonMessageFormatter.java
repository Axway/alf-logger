package io.axway.alf.formatter;

import java.util.function.*;
import io.axway.alf.Arguments;

public final class JsonMessageFormatter {
    private static final int DEFAULT_BUFFER_SIZE = 64;

    private static final JsonMessageFormatter INSTANCE = new JsonMessageFormatter();
    private static final char MESSAGE_SEPARATOR = ' ';

    public static JsonMessageFormatter getFormatter() {
        return INSTANCE;
    }

    public String format(String message) {
        return message;
    }

    public String format(String message, Throwable throwable) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);

        sb.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(sb).add("exception", throwable).end();

        return sb.toString();
    }

    public String format(String message, Consumer<Arguments> arguments) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);

        sb.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(sb).add("args", arguments).end();

        return sb.toString();
    }

    public String format(String message, Consumer<Arguments> arguments, Throwable throwable) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);

        sb.append(message).append(MESSAGE_SEPARATOR);
        new JsonWriter(sb).add("args", arguments).add("exception", throwable).end();

        return sb.toString();
    }

    private JsonMessageFormatter() {
    }
}
