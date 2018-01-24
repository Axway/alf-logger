package io.axway.alf.exception;

import java.io.*;
import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;

final class ArgumentSerializationHelper {
    static void writeArgumentsToStream(@Nullable Consumer<Arguments> argsConsumer, ObjectOutputStream out) throws IOException {
        List<Pair> arguments = new ArrayList<>();
        if (argsConsumer != null) {
            argsConsumer.accept(new Arguments() {
                @Override
                public Arguments add(String key, @Nullable Object value) {
                    arguments.add(new Pair(key, value));
                    return this;
                }
            });
        }
        out.writeInt(arguments.size());
        for (Pair pair : arguments) {
            out.writeUTF(pair.getKey());
            out.writeObject(pair.getValue());
        }
    }

    @Nullable
    static Consumer<Arguments> readArgumentsFromStream(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int argumentsLength = in.readInt();

        if (argumentsLength == 0) {
            return null;
        }

        List<Pair> arguments = new ArrayList<>();
        for (int i = 0; i < argumentsLength; i++) {
            arguments.add(new Pair(in.readUTF(), in.readObject()));
        }

        return a -> arguments.forEach(pair -> a.add(pair.getKey(), pair.getValue()));
    }

    private static final class Pair {
        private final String m_key;
        @Nullable
        private final Object m_value;

        private Pair(String key, @Nullable Object value) {
            m_key = key;
            m_value = value;
        }

        private String getKey() {
            return m_key;
        }

        @Nullable
        private Object getValue() {
            return m_value;
        }
    }

    private ArgumentSerializationHelper() {
        // Prevent instantiation
    }
}
