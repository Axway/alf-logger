package io.axway.alf.log4j2;

import java.util.function.*;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import io.axway.alf.Arguments;

import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;

/**
 * This class converts a message with arguments into a Log4j2 {@link Message}.
 * <p>
 * When a log message is ignored (e.g. not printed in log files), Log4j2 processes {@link MessageSupplier} objects
 * twice faster than {@link Message} objects (even if message is not formatted).
 * <p>
 * Having the same object implementing both {@link MessageSupplier} and {@link Message} is a bit faster than two
 * separate objects.
 * <p>
 * Benchmarks don't show significant performance improvements by implementing {@link StringBuilderFormattable} but it
 * can't hurt and seems to be the norm other implementations
 */
public final class Log4j2Message implements MessageSupplier, Message, StringBuilderFormattable {
    private final String m_message;
    private final Consumer<Arguments> m_args;
    private String m_formattedMessage;

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
    public Message get() {
        return this;
    }

    @Override
    public String getFormattedMessage() {
        if (m_formattedMessage == null) {
            m_formattedMessage = getFormatter().format(m_message, m_args);
        }
        return m_formattedMessage;
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        if (m_formattedMessage == null) {
            getFormatter().formatTo(buffer, m_message, m_args);
        } else {
            buffer.append(m_formattedMessage);
        }
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
