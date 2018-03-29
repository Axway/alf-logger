package io.axway.alf.slf4j;

import io.axway.alf.log.ILoggerFactory;
import io.axway.alf.log.Logger;

public final class Slf4jLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(clazz));
    }
}
