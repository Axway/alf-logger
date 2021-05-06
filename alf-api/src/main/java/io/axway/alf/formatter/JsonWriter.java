package io.axway.alf.formatter;

import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;
import io.axway.alf.exception.ExceptionWithArguments;

public final class JsonWriter implements Arguments {
    private static final String[] CONTROL_CHARS_ESCAPE_CODES = { //
            "\\u0000", "\\u0001", "\\u0002", "\\u0003",  //
            "\\u0004", "\\u0005", "\\u0006", "\\u0007",  //
            "\\b", "\\t", "\\n", "\\u000B",              //
            "\\f", "\\r", "\\u000E", "\\u000F",          //
            "\\u0010", "\\u0011", "\\u0012", "\\u0013",  //
            "\\u0014", "\\u0015", "\\u0016", "\\u0017",  //
            "\\u0018", "\\u0019", "\\u001A", "\\u001B",  //
            "\\u001C", "\\u001D", "\\u001E", "\\u001F"};

    private final StringBuilder m_sb;
    private String m_separator = "";

    public JsonWriter(StringBuilder sb) {
        m_sb = sb;
        m_sb.append('{');
    }

    @Override
    public JsonWriter add(String key, @Nullable Object value) {
        writeKey(key);
        writeObject(value);
        return this;
    }

    @Override
    public JsonWriter add(String key, @Nullable Consumer<Arguments> arguments) {
        writeKey(key);
        if (arguments != null) {
            writeConsumer(arguments);
        } else {
            m_sb.append("null");
        }
        return this;
    }

    @Override
    public Arguments add(String key, @Nullable String value) {
        writeKey(key);
        if (value != null) {
            writeString(value);
        } else {
            m_sb.append("null");
        }
        return this;
    }

    @Override
    public Arguments add(String key, int value) {
        writeKey(key);
        m_sb.append(value);
        return this;
    }

    @Override
    public Arguments add(String key, long value) {
        writeKey(key);
        m_sb.append(value);
        return this;
    }

    @Override
    public Arguments add(String key, double value) {
        writeKey(key);
        m_sb.append(value);
        return this;
    }

    public void end() {
        m_sb.append('}');
    }

    private void writeKey(String key) {
        m_sb.append(m_separator);
        m_separator = ", ";
        writeString(key);
        m_sb.append(": ");
    }

    private void writeObject(@Nullable Object object) {
        if (object instanceof Consumer) {
            @SuppressWarnings("unchecked") Consumer<Arguments> consumer = (Consumer<Arguments>) object;
            writeConsumer(consumer);
        } else if (object instanceof Collection) {
            m_sb.append('[');
            Collection<?> c = (Collection<?>) object;
            boolean first = true;
            for (Object o : c) {
                if (!first) {
                    m_sb.append(", ");
                } else {
                    first = false;
                }
                writeObject(o);
            }
            m_sb.append(']');
        } else if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;
            writeConsumer(args -> map.forEach((k, v) -> args.add(String.valueOf(k), v)));
        } else if (object instanceof Throwable) {
            writeThrowable((Throwable) object);
        } else if (object instanceof Number || object instanceof Boolean || object == null) {
            m_sb.append(object);
        } else {
            writeString(String.valueOf(object));
        }
    }

    private void writeConsumer(Consumer<Arguments> argsConsumer) {
        JsonWriter jsonWriter = new JsonWriter(m_sb);
        argsConsumer.accept(jsonWriter);
        jsonWriter.end();
    }

    private void writeThrowable(Throwable throwable) {
        writeConsumer(args -> {
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

    /**
     * Writes a String into the buffer.
     * <p/>
     * This methods needs to be fast because it's called for each key and for most of the values but it also needs to
     * escape special characters (even if it's uncommon it needs to be done).
     * <p/>
     * Reading each input string character, checking it and then writing it into the buffer is slow.
     * Checking if there's no character to escape and then printing it directly if possible is faster.
     * The fastest way (yet) is to print it directly and then fix escaping afterwards.
     * Also, having a mapping table for the control characters helps too.
     *
     * @param string String to write
     */
    private void writeString(String string) {
        m_sb.append('"');

        int start = m_sb.length();
        m_sb.append(string);
        int end = m_sb.length();

        for (int i = end - 1; i >= start; i--) {
            char c = m_sb.charAt(i);
            if (c < CONTROL_CHARS_ESCAPE_CODES.length) {
                m_sb.replace(i, i + 1, CONTROL_CHARS_ESCAPE_CODES[c]);
            } else if (c == '"' || c == '\\') {
                m_sb.insert(i, '\\');
            }
        }

        m_sb.append('"');
    }
}
