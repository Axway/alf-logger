package io.axway.alf.log4j;

import io.axway.alf.log.Logger;

public final class LoggerFactory {
    public static Logger getLogger(String name) {
        return new Log4JLogger(org.apache.log4j.Logger.getLogger(name));
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Log4JLogger(org.apache.log4j.Logger.getLogger(clazz));
    }

    private LoggerFactory() {
        // static method class only
    }
}
