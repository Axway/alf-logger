package io.axway.alf.log4j;

import java.util.*;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class TestAppender extends AppenderSkeleton {
    private static final Map<String, TestAppender> INSTANCES = new HashMap<>();

    static String pollLog(String name) {
        return INSTANCES.get(name).m_logs.poll();
    }

    static void clear() {
        INSTANCES.values().forEach(testAppender -> testAppender.m_logs.clear());
    }

    private final Deque<String> m_logs = new LinkedList<>();

    @Override
    public void setName(String name) {
        super.setName(name);
        INSTANCES.put(name, this);
    }

    @Override
    protected void append(LoggingEvent event) {
        m_logs.add(getLayout().format(event));
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
