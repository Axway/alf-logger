package io.axway.alf.log4j;

import java.util.function.*;
import io.axway.alf.Arguments;

import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;

public class LogMessage {
    private final String m_message;
    private final Consumer<Arguments> m_args;

    public LogMessage(String message, Consumer<Arguments> args) {
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
    public String toString() {
        return getFormatter().format(m_message, m_args);
    }
}
