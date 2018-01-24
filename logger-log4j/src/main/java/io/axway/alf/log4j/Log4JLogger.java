package io.axway.alf.log4j;

import java.util.function.*;
import io.axway.alf.Arguments;
import io.axway.alf.log.Logger;
import io.axway.alf.log4j.internal.LogMessage;

final class Log4JLogger implements Logger {
    private final org.apache.log4j.Logger m_logger;

    Log4JLogger(org.apache.log4j.Logger logger) {
        m_logger = logger;
    }

    @Override
    public void trace(String message) {
        m_logger.trace(message);
    }

    @Override
    public void trace(String message, Consumer<Arguments> arguments) {
        m_logger.trace(new LogMessage(message, arguments));
    }

    @Override
    public void trace(String message, Throwable throwable) {
        m_logger.trace(message, throwable);
    }

    @Override
    public void trace(String message, Consumer<Arguments> arguments, Throwable throwable) {
        m_logger.trace(new LogMessage(message, arguments), throwable);
    }

    @Override
    public void debug(String message) {
        m_logger.debug(message);
    }

    @Override
    public void debug(String message, Consumer<Arguments> arguments) {
        m_logger.debug(new LogMessage(message, arguments));
    }

    @Override
    public void debug(String message, Throwable throwable) {
        m_logger.debug(message, throwable);
    }

    @Override
    public void debug(String message, Consumer<Arguments> arguments, Throwable throwable) {
        m_logger.debug(new LogMessage(message, arguments), throwable);
    }

    @Override
    public void info(String message) {
        m_logger.info(message);
    }

    @Override
    public void info(String message, Consumer<Arguments> arguments) {
        m_logger.info(new LogMessage(message, arguments));
    }

    @Override
    public void info(String message, Throwable throwable) {
        m_logger.info(message, throwable);
    }

    @Override
    public void info(String message, Consumer<Arguments> arguments, Throwable throwable) {
        m_logger.info(new LogMessage(message, arguments), throwable);
    }

    @Override
    public void warn(String message) {
        m_logger.warn(message);
    }

    @Override
    public void warn(String message, Consumer<Arguments> arguments) {
        m_logger.warn(new LogMessage(message, arguments));
    }

    @Override
    public void warn(String message, Throwable throwable) {
        m_logger.warn(message, throwable);
    }

    @Override
    public void warn(String message, Consumer<Arguments> arguments, Throwable throwable) {
        m_logger.warn(new LogMessage(message, arguments), throwable);
    }

    @Override
    public void error(String message) {
        m_logger.error(message);
    }

    @Override
    public void error(String message, Consumer<Arguments> arguments) {
        m_logger.error(new LogMessage(message, arguments));
    }

    @Override
    public void error(String message, Throwable throwable) {
        m_logger.error(message, throwable);
    }

    @Override
    public void error(String message, Consumer<Arguments> arguments, Throwable throwable) {
        m_logger.error(new LogMessage(message, arguments), throwable);
    }

    @Override
    public String getName() {
        return m_logger.getName();
    }
}
