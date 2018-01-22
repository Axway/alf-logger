package io.axway.alf.internal;

import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.exception.ExceptionWithArguments;
import io.axway.alf.log.Arguments;

public final class JsonMessageFormatter {
    private static final JsonMessageFormatter INSTANCE = new JsonMessageFormatter();
    private static final char MESSAGE_SEPARATOR = ' ';

    public static JsonMessageFormatter getFormatter() {
        return INSTANCE;
    }

    public String format(String message) {
        return message;
    }

    public String format(String message, Throwable throwable) {
        return message + MESSAGE_SEPARATOR + "{\"exception\": " + formatThrowable(throwable) + "}";
    }

    public String format(String message, @Nullable Consumer<Arguments> arguments) {
        return message + MESSAGE_SEPARATOR + "{\"args\": " + formatArguments(arguments) + "}";
    }

    public String format(String message, @Nullable Consumer<Arguments> arguments, Throwable throwable) {
        return message + MESSAGE_SEPARATOR + "{\"args\": " + formatArguments(arguments) + ", \"exception\": " + formatThrowable(throwable) + "}";
    }

    private String formatThrowable(Throwable throwable) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\": \"");
        sb.append(throwable.getClass().getName());
        sb.append("\", \"message\": \"");
        sb.append(throwable instanceof ExceptionWithArguments ? ((ExceptionWithArguments) throwable).getRawMessage() : throwable.getMessage());
        sb.append("\"");
        if (throwable instanceof ExceptionWithArguments && ((ExceptionWithArguments) throwable).getArguments() != null) {
            sb.append(", \"args\": ");
            sb.append(formatArguments(((ExceptionWithArguments) throwable).getArguments()));
        }
        if (throwable.getCause() != null) {
            sb.append(", \"cause\": ");
            sb.append(formatThrowable(throwable.getCause()));
        }
        sb.append("}");
        return sb.toString();
    }

    private String formatArguments(@Nullable Consumer<Arguments> arguments) {
        StringBuilder sb = new StringBuilder("{");
        if (arguments != null) {
            arguments.accept(new ArgumentsImpl(sb));
        }
        sb.append("}");
        return sb.toString();
    }

    private static final class ArgumentsImpl implements Arguments {
        private final StringBuilder m_sb;
        private String m_separator = "";

        private ArgumentsImpl(StringBuilder sb) {
            m_sb = sb;
        }

        @Override
        public Arguments add(String key, @Nullable Object value) {
            writeKey(key);
            m_sb.append(format(value));
            return this;
        }

        @Override
        public Arguments add(String key, @Nullable String value) {
            writeKey(key);
            m_sb.append(format(value));
            return this;
        }

        @Override
        public Arguments add(String key, long value) {
            writeKey(key);
            m_sb.append(format(value));
            return this;
        }

        private void writeKey(String key) {
            m_sb.append(m_separator);
            m_separator = ", ";

            m_sb.append(quoted(key));
            m_sb.append(": ");
        }

        private static String quoted(CharSequence string) {
            int len = string.length();
            StringBuilder sb = new StringBuilder(len + 2);
            sb.append('"');
            for (int i = 0; i < len; i++) {
                char c = string.charAt(i);
                if (c < ' ') {
                    switch (c) {
                        case '\b':
                            sb.append("\\b");
                            break;
                        case '\t':
                            sb.append("\\t");
                            break;
                        case '\n':
                            sb.append("\\n");
                            break;
                        case '\f':
                            sb.append("\\f");
                            break;
                        case '\r':
                            sb.append("\\r");
                            break;
                        default:
                            String codePoint = "000" + Integer.toHexString(c);
                            sb.append("\\u").append(codePoint, codePoint.length() - 4, 4);
                    }
                } else if (c == '"' || c == '\\' || c == '/') {
                    sb.append('\\').append(c);
                } else {
                    sb.append(c);
                }
            }
            sb.append('"');
            return sb.toString();
        }

        private static String format(@Nullable Object object) {
            if (object instanceof String) {
                return quoted(String.valueOf(object));
            } else if (object instanceof Collection) {
                Collection<?> c = (Collection<?>) object;
                StringJoiner stringJoiner = new StringJoiner(", ");
                for (Object o : c) {
                    stringJoiner.add(format(o));
                }
                return '[' + stringJoiner.toString() + ']';
            } else {
                return String.valueOf(object);
            }
        }
    }

    private JsonMessageFormatter() {
    }
}
