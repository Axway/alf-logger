package io.axway.alf.log4j2;

import java.util.*;
import java.util.function.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.axway.alf.Arguments;
import io.axway.alf.exception.FormattedException;
import io.axway.alf.exception.FormattedRuntimeException;
import io.axway.alf.log.Logger;
import io.axway.alf.log.LoggerFactory;
import io.axway.alf.log4j2.layout.AlfJsonLayout;

import static org.apache.logging.log4j.Level.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("Duplicates")
public final class Log4j2LoggerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4j2LoggerTest.class);

    private static final PatternLayout TTCC_LAYOUT = PatternLayout.newBuilder().withPattern("%date{ISO8601} [%thread] %level %logger - %message%n").build();
    private static final TestAppender TTCC_APPENDER = registerTestAppender("TestTTCC", TTCC_LAYOUT);
    private static final TestAppender JSON_APPENDER = registerTestAppender("TestJSON", AlfJsonLayout.newBuilder().build());

    private static TestAppender registerTestAppender(String name, StringLayout layout) {
        TestAppender appender = new TestAppender(name, layout);
        appender.start();

        LoggerContext context = LoggerContext.getContext(false);
        context.getRootLogger().addAppender(appender);
        context.updateLoggers();

        return appender;
    }

    @BeforeMethod
    public void setUp() {
        TTCC_APPENDER.clear();
        JSON_APPENDER.clear();
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

        assertLog(JSON_APPENDER, "\"message\": \"Test\"}");
        assertLog(TTCC_APPENDER, "Test");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArguments(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("key", "value"));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"key\": \"value\"}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void logWithArgumentsToEscape(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("key", "val\"ue"));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"key\": \"val\\\"ue\"}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"val\\\"ue\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndThrowable(Level level) {
        Throwable throwable = new Throwable();
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLog(JSON_APPENDER,
                  "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": null}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndExceptionWithArguments(Level level) {
        Throwable throwable = new FormattedException("Oups", a -> a.add("error", "I did it again"));
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLog(JSON_APPENDER,
                  "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"io.axway.alf.exception.FormattedException\", \"message\": \"Oups\", \"args\": {\"error\": \"I did it again\"}}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndExceptionWithArgumentsAndCause(Level level) {
        Throwable throwable = new FormattedException("Oups", a -> a.add("error", "I did it again"), new FormattedRuntimeException("Root cause"));
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLog(JSON_APPENDER,
                  "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"io.axway.alf.exception.FormattedException\", \"message\": \"Oups\", \"args\": {\"error\": \"I did it again\"}, \"cause\": {\"type\": \"io.axway.alf.exception.FormattedRuntimeException\", \"message\": \"Root cause\"}}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithArgumentsAndThrowableWithMessage(Level level) {
        Throwable throwable = new Throwable("kaboom");
        getMessageArgumentAndExceptionMethod(level).accept("Test", a -> a.add("key", "value"), throwable);

        assertLog(JSON_APPENDER,
                  "\"message\": \"Test\", \"args\": {\"key\": \"value\"}, \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": \"kaboom\"}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"key\": \"value\"}}");
    }

    @Test(dataProvider = "logLevels")
    public void simpleLogWithThrowable(Level level) {
        Throwable throwable = new Throwable();
        getMessageAndThrowableMethod(level).accept("Test", throwable);

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"exception\": {\"type\": \"java.lang.Throwable\", \"message\": null}}");
        assertLog(TTCC_APPENDER, "Test");
    }

    @Test(dataProvider = "logLevels")
    public void shouldHandleNullValue(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("object", (Object) null).add("string", (String) null));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"object\": null, \"string\": null}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"object\": null, \"string\": null}}");
    }

    @Test(dataProvider = "logLevels")
    public void shouldNotDiscardExceptionWhenCallingArgument(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("A", "A").add("B", throwUponCall()));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"A\": \"A\"}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"A\": \"A\"}");
    }

    @Test(dataProvider = "logLevels")
    public void shouldNotDiscardExceptionWhenFormattingObject(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("A", "A").add("B", throwUponToString()));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"A\": \"A\"}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"A\": \"A\"}");
    }

    @Test(dataProvider = "logLevels")
    public void shouldNotDiscardExceptionWhenFormattingList(Level level) {
        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("A", "A").add("B", List.of("B1", "B2", throwUponToString(), "B4")));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"A\": \"A\", \"B\": [\"B1\", \"B2\"]");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"A\": \"A\", \"B\": [\"B1\", \"B2\"]");
    }

    @Test(dataProvider = "logLevels")
    public void shouldNotDiscardExceptionWhenFormattingMap(Level level) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("B1", "B1");
        map.put("B2", "B2");
        map.put("B3", throwUponToString());
        map.put("B4", "B4");

        getMessageAndArgumentMethod(level).accept("Test", a -> a.add("A", "A").add("B", map));

        assertLog(JSON_APPENDER, "\"message\": \"Test\", \"args\": {\"A\": \"A\", \"B\": {\"B1\": \"B1\", \"B2\": \"B2\"}}");
        assertLog(TTCC_APPENDER, "Test {\"args\": {\"A\": \"A\", \"B\": {\"B1\": \"B1\", \"B2\": \"B2\"}}");
    }

    @Test
    public void shouldCreateALoggerWithGivenName() {
        Logger logger = LoggerFactory.getLogger("customTest");
        assertThat(logger.getName()).isEqualTo("customTest");
    }

    @Test
    public void shouldCreateALoggerWithGivenClass() {
        Logger logger = LoggerFactory.getLogger(Log4j2LoggerTest.class);
        assertThat(logger.getName()).isEqualTo("io.axway.alf.log4j2.Log4j2LoggerTest");
    }

    private void assertLog(TestAppender appender, String content) {
        assertThat(appender.lastLog()).contains(content);
    }

    private static Consumer<String> getMessageMethod(Level level) {
        switch (level.getStandardLevel()) {
            case ERROR:
                return LOGGER::error;
            case WARN:
                return LOGGER::warn;
            case INFO:
                return LOGGER::info;
            case DEBUG:
                return LOGGER::debug;
            case TRACE:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static BiConsumer<String, Consumer<Arguments>> getMessageAndArgumentMethod(Level level) {
        switch (level.getStandardLevel()) {
            case ERROR:
                return LOGGER::error;
            case WARN:
                return LOGGER::warn;
            case INFO:
                return LOGGER::info;
            case DEBUG:
                return LOGGER::debug;
            case TRACE:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static BiConsumer<String, Throwable> getMessageAndThrowableMethod(Level level) {
        switch (level.getStandardLevel()) {
            case ERROR:
                return LOGGER::error;
            case WARN:
                return LOGGER::warn;
            case INFO:
                return LOGGER::info;
            case DEBUG:
                return LOGGER::debug;
            case TRACE:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static TriConsumer<String, Consumer<Arguments>, Throwable> getMessageArgumentAndExceptionMethod(Level level) {
        switch (level.getStandardLevel()) {
            case ERROR:
                return LOGGER::error;
            case WARN:
                return LOGGER::warn;
            case INFO:
                return LOGGER::info;
            case DEBUG:
                return LOGGER::debug;
            case TRACE:
                return LOGGER::trace;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private int throwUponCall() {
        throw new UnsupportedOperationException("call");
    }

    private Object throwUponToString() {
        return new Object() {
            @Override
            public String toString() {
                throw new UnsupportedOperationException("toString()");
            }
        };
    }

    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }
}
