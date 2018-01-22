package io.axway.alf.exception;

import java.io.*;
import java.util.function.*;
import io.axway.alf.internal.JsonMessageFormatter;
import io.axway.alf.log.Arguments;

import static io.axway.alf.exception.ArgumentSerializationHelper.*;
import static io.axway.alf.internal.JsonMessageFormatter.getFormatter;

/**
 * {@link RuntimeException} that will be formatted using current json formatter
 */
public class FormattedRuntimeException extends RuntimeException implements ExceptionWithArguments {
    private transient Consumer<Arguments> m_argsConsumer;

    public FormattedRuntimeException(String message) {
        super(message);
        m_argsConsumer = null;
    }

    public FormattedRuntimeException(Throwable cause, String message) {
        super(message, cause);
        m_argsConsumer = null;
    }

    public FormattedRuntimeException(String message, Consumer<Arguments> args) {
        super(message);
        m_argsConsumer = args;
    }

    public FormattedRuntimeException(Throwable cause, String message, Consumer<Arguments> args) {
        super(message, cause);
        m_argsConsumer = args;
    }

    @Override
    public String getMessage() {
        JsonMessageFormatter formatter = getFormatter();
        if (m_argsConsumer != null) {
            return formatter.format(super.getMessage(), m_argsConsumer);
        } else {
            return formatter.format(super.getMessage());
        }
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
