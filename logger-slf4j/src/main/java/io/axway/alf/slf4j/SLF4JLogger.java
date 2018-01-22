package io.axway.alf.slf4j;

import java.util.function.*;

import io.axway.alf.log.Arguments;
import io.axway.alf.log.Logger;

import static io.axway.alf.internal.JsonMessageFormatter.getFormatter;

public final class SLF4JLogger implements Logger {
    private final org.slf4j.Logger m_logger;

    public static Logger getLogger(String name) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    public static Logger getLogger(Class<?> clazz) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    public static Logger getLogger(org.slf4j.Logger logger) {
        return new SLF4JLogger(logger);
    }

    private SLF4JLogger(org.slf4j.Logger logger) {
        m_logger = logger;
    }

    @Override
    public void trace(String message) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message);
    }

    @Override
    public void trace(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, arguments);
    }

    @Override
    public void trace(String message, Throwable throwable) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, throwable);
    }

    @Override
    public void trace(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, arguments, throwable);
    }

    @Override
    public void debug(String message) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message);
    }

    @Override
    public void debug(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, arguments);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, throwable);
    }

    @Override
    public void debug(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, arguments, throwable);
    }

    @Override
    public void info(String message) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message);
    }

    @Override
    public void info(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, arguments);
    }

    @Override
    public void info(String message, Throwable throwable) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, throwable);
    }

    @Override
    public void info(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, arguments, throwable);
    }

    @Override
    public void warn(String message) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message);
    }

    @Override
    public void warn(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, arguments);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, throwable);
    }

    @Override
    public void warn(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, arguments, throwable);
    }

    @Override
    public void error(String message) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message);
    }

    @Override
    public void error(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, arguments);
    }

    @Override
    public void error(String message, Throwable throwable) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, throwable);
    }

    @Override
    public void error(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, arguments, throwable);
    }

    @Override
    public String getName() {
        return m_logger.getName();
    }

    private void doLog(Consumer<String> logFunction, Supplier<Boolean> isEnabled, String message) {
        if (isEnabled.get()) {
            logFunction.accept(getFormatter().format(message));
        }
    }

    private void doLog(Consumer<String> logFunction, Supplier<Boolean> isEnabled, String message, Consumer<Arguments> arguments) {
        if (isEnabled.get()) {
            logFunction.accept(getFormatter().format(message, arguments));
        }
    }

    private void doLog(BiConsumer<String, Throwable> logFunction, Supplier<Boolean> isEnabled, String message, Throwable throwable) {
        if (isEnabled.get()) {
            logFunction.accept(getFormatter().format(message, throwable), throwable);
        }
    }

    private void doLog(BiConsumer<String, Throwable> logFunction, Supplier<Boolean> isEnabled, String message, Consumer<Arguments> arguments,
                       Throwable throwable) {
        if (isEnabled.get()) {
            logFunction.accept(getFormatter().format(message, arguments, throwable), throwable);
        }
    }
}
