package io.axway.alf.log4j2;

import java.util.function.*;
import org.apache.logging.log4j.message.Message;
import io.axway.alf.Arguments;

import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;

public final class Log4j2Message implements Message {
    private final String m_message;
    private final Consumer<Arguments> m_args;

    Log4j2Message(String message, Consumer<Arguments> args) {
        m_message = message;
        m_args = args;
    }

    public String getMessage() {
        return m_message;
    }

    public Consumer<Arguments> getArgs() {
        return m_args;
    }

    @Override
    public String getFormattedMessage() {
        return getFormatter().format(m_message, m_args);
    }

    @Override
    public String getFormat() {
        return "";
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
