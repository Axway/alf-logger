package io.axway.alf.slf4j;

import io.axway.alf.log.Logger;

public final class LoggerFactory {

    public static Logger getLogger(String name) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    public static Logger getLogger(Class<?> clazz) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    private LoggerFactory() {
        // static method class only
    }
}
