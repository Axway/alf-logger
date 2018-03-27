package io.axway.alf.slf4j;

import io.axway.alf.log.ILoggerFactory;
import io.axway.alf.log.Logger;

public final class SLF4JLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(clazz));
    }
}
