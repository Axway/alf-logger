package io.axway.alf.slf4j;

import java.util.function.*;
import org.slf4j.helpers.MarkerIgnoringBase;

public class TestLogAdapter extends MarkerIgnoringBase {

    static TestLogAdapter captureTrace(Consumer<String> messageConsumer, Consumer<Throwable> throwableConsumer) {
        return new TestLogAdapter() {
            @Override
            public void trace(String msg) {
                messageConsumer.accept(msg);
            }

            @Override
            public void trace(String msg, Throwable throwable) {
                messageConsumer.accept(msg);
                throwableConsumer.accept(throwable);
            }
        };
    }

    static TestLogAdapter captureDebug(Consumer<String> messageConsumer, Consumer<Throwable> throwableConsumer) {
        return new TestLogAdapter() {
            @Override
            public void debug(String msg) {
                messageConsumer.accept(msg);
            }

            @Override
            public void debug(String msg, Throwable throwable) {
                messageConsumer.accept(msg);
                throwableConsumer.accept(throwable);
            }
        };
    }

    static TestLogAdapter captureInfo(Consumer<String> messageConsumer, Consumer<Throwable> throwableConsumer) {
        return new TestLogAdapter() {
            @Override
            public void info(String msg) {
                messageConsumer.accept(msg);
            }

            @Override
            public void info(String msg, Throwable throwable) {
                messageConsumer.accept(msg);
                throwableConsumer.accept(throwable);
            }
        };
    }

    static TestLogAdapter captureWarn(Consumer<String> messageConsumer, Consumer<Throwable> throwableConsumer) {
        return new TestLogAdapter() {
            @Override
            public void warn(String msg) {
                messageConsumer.accept(msg);
            }

            @Override
            public void warn(String msg, Throwable throwable) {
                messageConsumer.accept(msg);
                throwableConsumer.accept(throwable);
            }
        };
    }

    static TestLogAdapter captureError(Consumer<String> messageConsumer, Consumer<Throwable> throwableConsumer) {
        return new TestLogAdapter() {
            @Override
            public void error(String msg) {
                messageConsumer.accept(msg);
            }

            @Override
            public void error(String msg, Throwable throwable) {
                messageConsumer.accept(msg);
                throwableConsumer.accept(throwable);
            }
        };
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String msg) {
    }

    @Override
    public void trace(String format, Object arg) {
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
    }

    @Override
    public void trace(String format, Object... arguments) {
    }

    @Override
    public void trace(String msg, Throwable t) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String msg) {
    }

    @Override
    public void debug(String format, Object arg) {
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
    }

    @Override
    public void debug(String format, Object... arguments) {
    }

    @Override
    public void debug(String msg, Throwable t) {
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String msg) {
    }

    @Override
    public void info(String format, Object arg) {
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
    }

    @Override
    public void info(String format, Object... arguments) {
    }

    @Override
    public void info(String msg, Throwable t) {
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
    }

    @Override
    public void warn(String format, Object arg) {
    }

    @Override
    public void warn(String format, Object... arguments) {
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
    }

    @Override
    public void warn(String msg, Throwable t) {
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
    }

    @Override
    public void error(String format, Object arg) {
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
    }

    @Override
    public void error(String format, Object... arguments) {
    }

    @Override
    public void error(String msg, Throwable t) {
    }
}
