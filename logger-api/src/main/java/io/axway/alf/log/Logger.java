package io.axway.alf.log;

import java.util.function.*;

import static io.axway.alf.internal.JsonMessageFormatter.getFormatter;

/**
 * Main entry point of the Log API.<br/>
 * It provide a new logging API on top of the SLF4J API. {@see org.slf4j.Logger}<br/>
 * It use the json formatter instance to format message before forwarding them to SLF4J.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Logger {
    private final org.slf4j.Logger m_logger;

    public static Logger getLogger(String name) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(name));
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    public static Logger getLogger(org.slf4j.Logger logger) {
        return new Logger(logger);
    }

    private Logger(org.slf4j.Logger logger) {
        m_logger = logger;
    }

    /**
     * Log a message at the TRACE level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     */
    public void trace(String message) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message);
    }

    /**
     * Log a message at the TRACE level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    public void trace(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, arguments);
    }

    /**
     * Log a message at the TRACE level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    public void trace(String message, Throwable throwable) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, throwable);
    }

    /**
     * Log a message at the TRACE level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    public void trace(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::trace, m_logger::isTraceEnabled, message, arguments, throwable);
    }

    /**
     * Log a message at the DEBUG level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     */
    public void debug(String message) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message);
    }

    /**
     * Log a message at the DEBUG level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    public void debug(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, arguments);
    }

    /**
     * Log a message at the DEBUG level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    public void debug(String message, Throwable throwable) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, throwable);
    }

    /**
     * Log a message at the DEBUG level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    public void debug(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::debug, m_logger::isDebugEnabled, message, arguments, throwable);
    }

    /**
     * Log a message at the INFO level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     */
    public void info(String message) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message);
    }

    /**
     * Log a message at the INFO level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    public void info(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, arguments);
    }

    /**
     * Log a message at the INFO level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    public void info(String message, Throwable throwable) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, throwable);
    }

    /**
     * Log a message at the INFO level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    public void info(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::info, m_logger::isInfoEnabled, message, arguments, throwable);
    }

    /**
     * Log a message at the WARN level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     */
    public void warn(String message) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message);
    }

    /**
     * Log a message at the WARN level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    public void warn(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, arguments);
    }

    /**
     * Log a message at the WARN level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    public void warn(String message, Throwable throwable) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, throwable);
    }

    /**
     * Log a message at the WARN level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    public void warn(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::warn, m_logger::isWarnEnabled, message, arguments, throwable);
    }

    /**
     * Log a message at the ERROR level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     */
    public void error(String message) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message);
    }

    /**
     * Log a message at the ERROR level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    public void error(String message, Consumer<Arguments> arguments) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, arguments);
    }

    /**
     * Log a message at the ERROR level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    public void error(String message, Throwable throwable) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, throwable);
    }

    /**
     * Log a message at the ERROR level.<br/>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    public void error(String message, Consumer<Arguments> arguments, Throwable throwable) {
        doLog(m_logger::error, m_logger::isErrorEnabled, message, arguments, throwable);
    }

    /**
     * Return the name of this <code>Logger</code> instance.
     *
     * @return name of this logger instance
     */
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
