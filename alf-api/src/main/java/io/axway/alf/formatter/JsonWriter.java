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
    // This is where we can go back in case of exception
    private int m_safePoint;
    // Comparing this to the safe point tells if we need to add the comma separator
    private int m_startOfObject;

    public JsonWriter(StringBuilder sb) {
        m_sb = sb;
        m_sb.append('{');
        m_safePoint = m_startOfObject = m_sb.length();
    }

    @Override
    public JsonWriter add(String key, @Nullable Object value) {
        writeKey(key);
        try {
            writeObject(value);
            markSafePoint();
        } finally {
            resetToLastSafePoint();
        }
        return this;
    }

    @Override
    public JsonWriter add(String key, @Nullable Consumer<Arguments> arguments) {
        writeKey(key);
        if (arguments != null) {
            writeConsumer(arguments);
        } else {
            m_sb.append("null");
            markSafePoint();
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
        markSafePoint();
        return this;
    }

    @Override
    public Arguments add(String key, int value) {
        writeKey(key);
        m_sb.append(value);
        markSafePoint();
        return this;
    }

    @Override
    public Arguments add(String key, long value) {
        writeKey(key);
        m_sb.append(value);
        markSafePoint();
        return this;
    }

    @Override
    public Arguments add(String key, double value) {
        writeKey(key);
        m_sb.append(value);
        markSafePoint();
        return this;
    }

    public void end() {
        m_sb.append('}');
    }

    private void markSafePoint() {
        m_safePoint = m_sb.length();
    }

    private void resetToLastSafePoint() {
        final int safePoint = m_safePoint;
        if (m_sb.length() > safePoint) {
            m_sb.setLength(safePoint);
        }
    }

    private void writeKey(String key) {
        if (m_startOfObject != m_safePoint) {
            m_sb.append(", ");
        }
        writeString(key);
        m_sb.append(": ");
    }

    private void writeObject(@Nullable Object object) {
        if (object instanceof Consumer) {
            @SuppressWarnings("unchecked") Consumer<Arguments> consumer = (Consumer<Arguments>) object;
            writeConsumer(consumer);
        } else if (object instanceof Collection) {
            m_sb.append('[');
            markSafePoint();
            try {
                Iterator<?> iterator = ((Collection<?>) object).iterator();
                if (iterator.hasNext()) {
                    writeObject(iterator.next());
                    markSafePoint();
                }
                while (iterator.hasNext()) {
                    m_sb.append(", ");
                    writeObject(iterator.next());
                    markSafePoint();
                }
            } finally {
                resetToLastSafePoint();
                m_sb.append(']');
                markSafePoint();
            }
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
        // Keep parent start before creating child object
        int startOfObject = m_startOfObject;
        // Start new object
        m_sb.append('{');
        m_safePoint = m_startOfObject = m_sb.length();
        try {
            argsConsumer.accept(this);
            markSafePoint();
        } finally {
            resetToLastSafePoint();
            // Close object and continue parent object
            m_sb.append('}');
            m_startOfObject = startOfObject;
            markSafePoint();
        }
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
