package io.axway.alf.log4j2;

import org.apache.logging.log4j.LogManager;
import io.axway.alf.log.ILoggerFactory;
import io.axway.alf.log.Logger;

public class Log4j2LoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new Log4j2Logger(LogManager.getLogger(name));
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Log4j2Logger(LogManager.getLogger(clazz));
    }
}
