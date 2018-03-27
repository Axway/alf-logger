package io.axway.alf.log4j;

import java.util.function.*;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.axway.alf.Arguments;
import io.axway.alf.exception.FormattedException;
import io.axway.alf.exception.FormattedRuntimeException;
import io.axway.alf.log.Logger;
import io.axway.alf.log.LoggerFactory;

import static org.apache.log4j.Level.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("Duplicates")
public class Log4JLoggerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log4JLoggerTest.class);

    @BeforeMethod
    public void setUp() throws Exception {
        TestAppender.clear();
    }

    @DataProvider
    public Object[][] logLevels() {
        return new Object[][]{ //
                {ERROR}, //
                {WARN}, //
                {INFO}, //
                {DEBUG}, //
                {TRACE}, //
        };
    }

    @Test(dataProvider = "logLevels")
    public void simpleLog(Level level) {
        getMessageMethod(level).accept("Test");

        assertLogJson("\"message\": \"Test\"}");
        assertLogTTCC("Test");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArguments(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("key", "value"));

        assertLogJson("\"message\": \"Test\", \"args\": {\"key\": \"value\"}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void logWithArgumentsToEscape(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("key", "val\"ue"));

        assertLogJson("\"message\": \"Test\", \"args\": {\"key\": \"val\\\"ue\"}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"val\\\"ue\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndThrowable(Level level) {
        Throwable throwable = new Throwable();
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLogJson("\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": null}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndExceptionWithArguments(Level level) {
        Throwable throwable = new FormattedException("Oups", a -> a.add("error", "I did it again"));
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLogJson(
                "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"io.axway.alf.exception.FormattedException\", \"message\": \"Oups\", \"args\": {\"error\": \"I did it again\"}}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndExceptionWithArgumentsAndCause(Level level) {
        Throwable throwable = new FormattedException(new FormattedRuntimeException("Root cause"), "Oups", a -> a.add("error", "I did it again"));
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLogJson(
                "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"io.axway.alf.exception.FormattedException\", \"message\": \"Oups\", \"args\": {\"error\": \"I did it again\"}, \"cause\": {\"type\": \"io.axway.alf.exception.FormattedRuntimeException\", \"message\": \"Root cause\"}}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndThrowableWithMessage(Level level) {
        Throwable throwable = new Throwable("kaboom");
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLogJson("\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": \"kaboom\"}}");
        assertLogTTCC("Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithThrowable(Level level) {
        Throwable throwable = new Throwable();
        getMessageAndThrowableMethod(level).accept("Test", throwable);

        assertLogJson("\"message\": \"Test\", \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": null}}");
        assertLogTTCC("Test");
    }

    @Test(dataProvider = "logLevels")
    public void shouldHandleNullValue(Level level) {
        //noinspection RedundantCast
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("object", (Object) null).add("string", (String) null));

        assertLogJson("\"message\": \"Test\", \"args\": {\"object\": null, \"string\": null}}");
        assertLogTTCC("Test {\"args\": {\"object\": null, \"string\": null}}");
    }

    @Test
    public void shouldCreateALoggerWithGivenName() {
        Logger logger = LoggerFactory.getLogger("customTest");
        assertThat(logger.getName()).isEqualTo("customTest");
    }

    @Test
    public void shouldCreateALoggerWithGivenClass() {
        Logger logger = LoggerFactory.getLogger(Log4JLoggerTest.class);
        assertThat(logger.getName()).isEqualTo("io.axway.alf.log4j.Log4JLoggerTest");
    }

    private void assertLogJson(String end) {
        String lastLog = TestAppender.pollLog("TestJson");

        assertThat(lastLog).isNotNull();
        assertThat(lastLog).endsWith(end + Layout.LINE_SEP);
    }

    private void assertLogTTCC(String end) {
        String lastLog = TestAppender.pollLog("TestTTCC");

        assertThat(lastLog).isNotNull();
        assertThat(lastLog).endsWith(end + Layout.LINE_SEP);
    }

    private static Consumer<String> getMessageMethod(Level level) {
        switch (level.toInt()) {
            case ERROR_INT:
                return LOGGER::error;
            case WARN_INT:
                return LOGGER::warn;
            case INFO_INT:
                return LOGGER::info;
            case DEBUG_INT:
                return LOGGER::debug;
            case TRACE_INT:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static BiConsumer<String, Consumer<Arguments>> getMessageAndArgumentMethod(Level level) {
        switch (level.toInt()) {
            case ERROR_INT:
                return LOGGER::error;
            case WARN_INT:
                return LOGGER::warn;
            case INFO_INT:
                return LOGGER::info;
            case DEBUG_INT:
                return LOGGER::debug;
            case TRACE_INT:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static BiConsumer<String, Throwable> getMessageAndThrowableMethod(Level level) {
        switch (level.toInt()) {
            case ERROR_INT:
                return LOGGER::error;
            case WARN_INT:
                return LOGGER::warn;
            case INFO_INT:
                return LOGGER::info;
            case DEBUG_INT:
                return LOGGER::debug;
            case TRACE_INT:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static TriConsumer<String, Consumer<Arguments>, Throwable> getMessageArgumentAndExceptionMethod(Level level) {
        switch (level.toInt()) {
            case ERROR_INT:
                return LOGGER::error;
            case WARN_INT:
                return LOGGER::warn;
            case INFO_INT:
                return LOGGER::info;
            case DEBUG_INT:
                return LOGGER::debug;
            case TRACE_INT:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }
}
