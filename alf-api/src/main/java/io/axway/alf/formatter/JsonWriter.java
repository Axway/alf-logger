package io.axway.alf.formatter;

import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;
import io.axway.alf.exception.ExceptionWithArguments;

public final class JsonWriter implements Arguments {
    private final StringBuilder m_sb;
    private String m_separator = "";

    public JsonWriter(StringBuilder sb) {
        m_sb = sb;
        m_sb.append("{");
    }

    @Override
    public JsonWriter add(String key, @Nullable Object value) {
        m_sb.append(m_separator);
        m_separator = ", ";
        write(key);
        m_sb.append(": ");
        write(value);
        return this;
    }

    public void end() {
        m_sb.append("}");
    }

    private void write(@Nullable Object object) {
        if (object instanceof Collection) {
            m_sb.append('[');
            Collection<?> c = (Collection<?>) object;
            boolean first = true;
            for (Object o : c) {
                if (!first) {
                    m_sb.append(", ");
                } else {
                    first = false;
                }
                write(o);
            }
            m_sb.append(']');
        } else if (object instanceof Consumer<?>) {
            // assume it's a Consumer<Arguments>
            //noinspection unchecked
            write((Consumer<Arguments>) object);
        } else if (object instanceof Throwable) {
            write((Throwable) object);
        } else if (object instanceof Number || object instanceof Boolean || object == null) {
            m_sb.append(String.valueOf(object));
        } else {
            write(String.valueOf(object));
        }
    }

    private void write(Consumer<Arguments> argsConsumer) {
        JsonWriter jsonWriter = new JsonWriter(m_sb);
        argsConsumer.accept(jsonWriter);
        jsonWriter.end();
    }

    private void write(Throwable throwable) {
        write(args -> {
            args.add("type", throwable.getClass().getName());
            args.add("message", throwable instanceof ExceptionWithArguments ? ((ExceptionWithArguments) throwable).getRawMessage() : throwable.getMessage());

            if (throwable instanceof ExceptionWithArguments) {
                Consumer<Arguments> throwableArgs = ((ExceptionWithArguments) throwable).getArguments();
                if (throwableArgs != null) {
                    args.add("args", throwableArgs);
                }
            }
            Throwable cause = throwable.getCause();
            if (cause != null) {
                args.add("cause", cause);
            }
        });
    }

    private void write(CharSequence string) {
        int len = string.length();
        m_sb.append('"');
        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if (c < ' ') {
                switch (c) {
                    case '\b':
                        m_sb.append("\\b");
                        break;
                    case '\t':
                        m_sb.append("\\t");
                        break;
                    case '\n':
                        m_sb.append("\\n");
                        break;
                    case '\f':
                        m_sb.append("\\f");
                        break;
                    case '\r':
                        m_sb.append("\\r");
                        break;
                    default:
                        String codePoint = "000" + Integer.toHexString(c);
                        m_sb.append("\\u").append(codePoint, codePoint.length() - 4, 4);
                        break;
                }
            } else if (c == '"' || c == '\\') {
                m_sb.append('\\').append(c);
            } else {
                m_sb.append(c);
            }
        }
        m_sb.append('"');
    }
}
