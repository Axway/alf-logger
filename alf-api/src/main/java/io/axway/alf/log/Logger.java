package io.axway.alf.log;

import java.util.function.*;
import io.axway.alf.Arguments;

/**
 * Logger API.
 */
public interface Logger {

    /**
     * Log a message at the TRACE level.
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     */
    void trace(String message);

    /**
     * Log a message at the TRACE level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    void trace(String message, Consumer<Arguments> arguments);

    /**
     * Log a message at the TRACE level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    void trace(String message, Throwable throwable);

    /**
     * Log a message at the TRACE level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    void trace(String message, Consumer<Arguments> arguments, Throwable throwable);

    /**
     * Log a message at the DEBUG level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     */
    void debug(String message);

    /**
     * Log a message at the DEBUG level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    void debug(String message, Consumer<Arguments> arguments);

    /**
     * Log a message at the DEBUG level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    void debug(String message, Throwable throwable);

    /**
     * Log a message at the DEBUG level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the DEBUG level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    void debug(String message, Consumer<Arguments> arguments, Throwable throwable);

    /**
     * Log a message at the INFO level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     */
    void info(String message);

    /**
     * Log a message at the INFO level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    void info(String message, Consumer<Arguments> arguments);

    /**
     * Log a message at the INFO level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    void info(String message, Throwable throwable);

    /**
     * Log a message at the INFO level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    void info(String message, Consumer<Arguments> arguments, Throwable throwable);

    /**
     * Log a message at the WARN level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     */
    void warn(String message);

    /**
     * Log a message at the WARN level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    void warn(String message, Consumer<Arguments> arguments);

    /**
     * Log a message at the WARN level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    void warn(String message, Throwable throwable);

    /**
     * Log a message at the WARN level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the WARN level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    void warn(String message, Consumer<Arguments> arguments, Throwable throwable);

    /**
     * Log a message at the ERROR level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     */
    void error(String message);

    /**
     * Log a message at the ERROR level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     */
    void error(String message, Consumer<Arguments> arguments);

    /**
     * Log a message at the ERROR level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param throwable Throwable that will be joined to the message
     */
    void error(String message, Throwable throwable);

    /**
     * Log a message at the ERROR level.<p>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     *
     * @param message Constant message that represent an action
     * @param arguments Message arguments that will be joined to the message
     * @param throwable Throwable that will be joined to the message
     */
    void error(String message, Consumer<Arguments> arguments, Throwable throwable);

    /**
     * Return the name of this <code>Logger</code> instance.
     *
     * @return name of this logger instance
     */
    String getName();
}
