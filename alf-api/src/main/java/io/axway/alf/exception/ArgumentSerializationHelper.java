package io.axway.alf.exception;

import java.io.*;
import java.util.*;
import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;

final class ArgumentSerializationHelper {
    static void writeArgumentsToStream(@Nullable Consumer<Arguments> argsConsumer, ObjectOutputStream out) throws IOException {
        SerializableArguments instance = new SerializableArguments();
        if (argsConsumer != null) {
            argsConsumer.accept(instance);
        }
        instance.writeExternal(out);
    }

    @Nullable
    static Consumer<Arguments> readArgumentsFromStream(ObjectInputStream in) throws IOException, ClassNotFoundException {
        SerializableArguments instance = new SerializableArguments();
        instance.readExternal(in);
        return instance.m_arguments.isEmpty() ? null : instance.asArgumentsConsumer();
    }

    public static final class SerializableArguments implements Arguments, Externalizable {
        private final List<SerializableArgument> m_arguments = new ArrayList<>();

        @Override
        public Arguments add(String key, @Nullable Object value) {
            m_arguments.add(new SerializableArgument(key, value));
            return this;
        }

        @Override
        public Arguments add(String key, @Nullable Consumer<Arguments> arguments) {
            if (arguments == null) {
                m_arguments.add(new SerializableArgument(key, null));
            } else {
                SerializableArguments child = new SerializableArguments();
                arguments.accept(child);
                m_arguments.add(new SerializableArgument(key, child));
            }
            return this;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(m_arguments.size());
            for (SerializableArgument argument : m_arguments) {
                out.writeUTF(argument.getKey());
                out.writeObject(argument.getValue());
            }
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                m_arguments.add(new SerializableArgument(in.readUTF(), in.readObject()));
            }
        }

        private Consumer<Arguments> asArgumentsConsumer() {
            return args -> m_arguments.forEach(a -> {
                if (a.getValue() instanceof SerializableArguments) {
                    SerializableArguments value = (SerializableArguments) a.getValue();
                    args.add(a.getKey(), value.asArgumentsConsumer());
                } else {
                    args.add(a.getKey(), a.getValue());
                }
            });
        }
    }

    private static final class SerializableArgument {
        private final String m_key;
        @Nullable
        private final Object m_value;

        private SerializableArgument(String key, @Nullable Object value) {
            m_key = key;
            m_value = value;
        }

        public String getKey() {
            return m_key;
        }

        @Nullable
        public Object getValue() {
            return m_value;
        }
    }
}
