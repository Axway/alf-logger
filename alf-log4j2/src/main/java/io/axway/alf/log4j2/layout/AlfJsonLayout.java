package io.axway.alf.log4j2.layout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.*;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import io.axway.alf.formatter.JsonWriter;
import io.axway.alf.log4j2.Log4j2Message;

@SuppressWarnings({"WeakerAccess", "unused"})
@Plugin(name = "AlfJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public final class AlfJsonLayout extends AbstractStringLayout {
    public static class Builder<B extends Builder<B>> extends AbstractStringLayout.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<AlfJsonLayout> {
        @Nullable
        @PluginBuilderAttribute("dateFormat")
        private String m_dateFormat;

        @PluginBuilderAttribute("threadPrinting")
        private boolean m_threadPrinting = true;

        @PluginBuilderAttribute("levelPrinting")
        private boolean m_levelPrinting = true;

        @PluginBuilderAttribute("loggerPrinting")
        private boolean m_loggerPrinting = true;

        @PluginBuilderAttribute("contextPrinting")
        private boolean m_contextPrinting = true;

        @PluginBuilderAttribute("messageKey")
        private String m_messageKey = "message";

        public Builder() {
            super();
            setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public AlfJsonLayout build() {
            DateFormat dateFormat = m_dateFormat == null ? null : new SimpleDateFormat(m_dateFormat);
            return new AlfJsonLayout(getConfiguration(), getCharset(), getHeaderSerializer(), getFooterSerializer(), dateFormat, m_threadPrinting,
                                     m_levelPrinting, m_loggerPrinting, m_contextPrinting, m_messageKey);
        }

        public Builder withDateFormat(String dateFormat) {
            m_dateFormat = dateFormat;
            return this;
        }

        public Builder withThreadPrinting(boolean threadPrinting) {
            m_threadPrinting = threadPrinting;
            return this;
        }

        public Builder withLevelPrinting(boolean levelPrinting) {
            m_levelPrinting = levelPrinting;
            return this;
        }

        public Builder withLoggerPrinting(boolean loggerPrinting) {
            m_loggerPrinting = loggerPrinting;
            return this;
        }

        public Builder withContextPrinting(boolean contextPrinting) {
            m_contextPrinting = contextPrinting;
            return this;
        }

        public Builder withMessageKey(String messageKey) {
            m_messageKey = messageKey;
            return this;
        }
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }

    @Nullable
    private final DateFormat m_dateFormat;
    private final boolean m_threadPrinting;
    private final boolean m_levelPrinting;
    private final boolean m_loggerPrinting;
    private final boolean m_contextPrinting;
    private final String m_messageKey;

    public AlfJsonLayout(Configuration config, Charset aCharset, Serializer headerSerializer, Serializer footerSerializer, @Nullable DateFormat dateFormat,
                         boolean threadPrinting, boolean levelPrinting, boolean loggerPrinting, boolean contextPrinting, String messageKey) {
        super(config, aCharset, headerSerializer, footerSerializer);
        m_dateFormat = dateFormat;
        m_threadPrinting = threadPrinting;
        m_levelPrinting = levelPrinting;
        m_loggerPrinting = loggerPrinting;
        m_contextPrinting = contextPrinting;
        m_messageKey = messageKey;
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder sb = new StringBuilder(128);
        JsonWriter jsonWriter = new JsonWriter(sb);

        if (m_dateFormat != null) {
            jsonWriter.add("time", m_dateFormat.format(new Date(event.getTimeMillis())));
        } else {
            jsonWriter.add("time", event.getTimeMillis());
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
            ReadOnlyStringMap context = event.getContextData();
            if (!context.isEmpty()) {
                jsonWriter.add("context", args -> context.forEach(args::add));
            }
        }

        Message message = event.getMessage();
        if (message instanceof Log4j2Message) {
            Log4j2Message log4j2Message = (Log4j2Message) message;
            jsonWriter.add(m_messageKey, log4j2Message.getMessage());
            jsonWriter.add("args", log4j2Message.getArgs());
        } else {
            jsonWriter.add(m_messageKey, message.getFormattedMessage());
        }

        Throwable exception = event.getThrown() != null ? event.getThrown() : message.getThrowable();
        if (exception != null) {
            jsonWriter.add("exception", exception);
        }

        jsonWriter.end();

        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
