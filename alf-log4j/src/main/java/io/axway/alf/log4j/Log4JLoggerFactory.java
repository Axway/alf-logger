package io.axway.alf.log4j;

import io.axway.alf.log.ILoggerFactory;
import io.axway.alf.log.Logger;

public final class Log4JLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new Log4JLogger(org.apache.log4j.Logger.getLogger(name));
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Log4JLogger(org.apache.log4j.Logger.getLogger(clazz));
    }
}
