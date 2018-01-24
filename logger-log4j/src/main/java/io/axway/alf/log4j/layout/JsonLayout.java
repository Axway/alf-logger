package io.axway.alf.log4j.layout;

import java.util.*;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.spi.LoggingEvent;
import io.axway.alf.formatter.JsonWriter;
import io.axway.alf.log4j.internal.LogMessage;

public class JsonLayout extends DateLayout {

    private boolean m_threadPrinting = true;
    private boolean m_levelPrinting = true;
    private boolean m_loggerPrinting = true;
    private boolean m_contextPrinting = true;
    private String m_messageKey = "message";

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public String format(LoggingEvent event) {
        Object message = event.getMessage();
        Throwable throwable = null;
        if (event.getThrowableInformation() != null) {
            throwable = event.getThrowableInformation().getThrowable();
        }

        StringBuilder sb = new StringBuilder(128);
        JsonWriter jsonWriter = new JsonWriter(sb);

        if (dateFormat != null) {
            jsonWriter.add("time", dateFormat.format(new Date(event.getTimeStamp())));
        } else {
            jsonWriter.add("time", event.getTimeStamp());
        }

        if (m_threadPrinting) {
            jsonWriter.add("thread", event.getThreadName());
        }

        if (m_levelPrinting) {
            jsonWriter.add("level", event.getLevel());
        }

        if (m_loggerPrinting) {
            jsonWriter.add("logger", event.getLoggerName());
        }

        if (m_contextPrinting) {
            String ndc = event.getNDC();
            if (ndc != null) {
                jsonWriter.add("ndc", ndc);
            }
        }

        if (message instanceof LogMessage) {
            LogMessage logMessage = (LogMessage) message;
            jsonWriter.add(m_messageKey, logMessage.getMessage());
            jsonWriter.add("args", logMessage.getArgs());
        } else {
            jsonWriter.add("message", message);
        }

        if (throwable != null) {
            jsonWriter.add("exception", throwable);
        }

        jsonWriter.end();

        sb.append(LINE_SEP);
        return sb.toString();
    }

    public boolean isThreadPrinting() {
        return m_threadPrinting;
    }

    public void setThreadPrinting(boolean threadPrinting) {
        m_threadPrinting = threadPrinting;
    }

    public boolean isLevelPrinting() {
        return m_levelPrinting;
    }

    public void setLevelPrinting(boolean levelPrinting) {
        m_levelPrinting = levelPrinting;
    }

    public boolean isLoggerPrinting() {
        return m_loggerPrinting;
    }

    public void setLoggerPrinting(boolean loggerPrinting) {
        m_loggerPrinting = loggerPrinting;
    }

    public boolean isContextPrinting() {
        return m_contextPrinting;
    }

    public void setContextPrinting(boolean contextPrinting) {
        m_contextPrinting = contextPrinting;
    }

    public String getMessageKey() {
        return m_messageKey;
    }

    public void setMessageKey(String messageKey) {
        m_messageKey = messageKey;
    }
}
