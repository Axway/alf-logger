package io.axway.alf.exception;

import java.io.*;
import java.util.function.*;
import io.axway.alf.internal.JsonMessageFormatter;
import io.axway.alf.log.Arguments;

import static io.axway.alf.exception.ArgumentSerializationHelper.*;
import static io.axway.alf.internal.JsonMessageFormatter.getFormatter;

/**
 * {@link Exception} that will be formatted using the json formatter
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class FormattedException extends Exception implements ExceptionWithArguments {
    private transient Consumer<Arguments> m_argsConsumer;

    public FormattedException(String message) {
        super(message);
        m_argsConsumer = null;
    }

    public FormattedException(Throwable cause, String message) {
        super(message, cause);
        m_argsConsumer = null;
    }

    public FormattedException(String message, Consumer<Arguments> args) {
        super(message);
        m_argsConsumer = args;
    }

    public FormattedException(Throwable cause, String message, Consumer<Arguments> args) {
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
