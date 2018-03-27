package io.axway.alf.exception;

import java.io.*;
import java.util.function.*;
import io.axway.alf.Arguments;

import static io.axway.alf.exception.ArgumentSerializationHelper.*;
import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;

/**
 * {@link RuntimeException} that will be formatted using the JSON formatter
 */
public class FormattedRuntimeException extends RuntimeException implements ExceptionWithArguments {
    private transient Consumer<Arguments> m_argsConsumer = null;

    public FormattedRuntimeException() {
    }

    public FormattedRuntimeException(Throwable cause) {
        super(cause);
    }

    public FormattedRuntimeException(String message) {
        super(message);
    }

    public FormattedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormattedRuntimeException(String message, Consumer<Arguments> args) {
        super(message);
        m_argsConsumer = args;
    }

    public FormattedRuntimeException(String message, Consumer<Arguments> args, Throwable cause) {
        super(message, cause);
        m_argsConsumer = args;
    }

    @Override
    public String getMessage() {
        return getFormatter().formatException(this);
    }

    @Override
    public Consumer<Arguments> getArguments() {
        return m_argsConsumer;
    }

    @Override
    public String getRawMessage() {
        return super.getMessage();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeArgumentsToStream(m_argsConsumer, out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        m_argsConsumer = readArgumentsFromStream(in);
    }
}
