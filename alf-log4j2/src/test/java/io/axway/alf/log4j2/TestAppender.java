package io.axway.alf.log4j2;

import javax.annotation.*;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractAppender;

final class TestAppender extends AbstractAppender {
    private final StringLayout m_layout;
    @Nullable
    private volatile String m_lastLog;

    TestAppender(String name, StringLayout layout) {
        super(name, null, layout);
        m_layout = layout;
    }

    @Override
    public void append(LogEvent event) {
        m_lastLog = m_layout.toSerializable(event);
    }

    void clear() {
        m_lastLog = null;
    }

    @Nullable
    String lastLog() {
        return m_lastLog;
    }
}
