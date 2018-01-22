package io.axway.alf.exception;

import java.io.*;
import java.util.*;
import java.util.function.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.axway.alf.log.Arguments;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattedExceptionTest {
    @DataProvider(name = "exceptionProvider")
    public Object[][] exceptionProvider() {
        return new Object[][]{ //
                {FormattedException.class}, //
                {FormattedRuntimeException.class}, //
                {IllegalArgumentFormattedException.class}, //
                {IllegalStateFormattedException.class}, //
                {NullPointerFormattedException.class}, //
        };
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldFormatMessage(Class<?> clazz) throws Exception {
        Exception exception = (Exception) clazz.getDeclaredConstructor(String.class).newInstance("Kaboom");
        assertThat(exception) //
                .hasMessage("Kaboom") //
                .isInstanceOf(clazz);
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldFormatMessageAndException(Class<?> clazz) throws Exception {
        Exception cause = new Exception("initial stack");
        Exception exception = (Exception) clazz.getDeclaredConstructor(Throwable.class, String.class).newInstance(cause, "Kaboom");
        assertThat(exception) //
                .hasMessage("Kaboom") //
                .isInstanceOf(clazz) //
                .hasCause(cause);
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldFormatArguments(Class<?> clazz) throws Exception {
        Exception exception = (Exception) clazz.getDeclaredConstructor(String.class, Consumer.class)
                .newInstance("Kaboom", (Consumer<Arguments>) a -> a.add("myKey", "myValue").add("anotherKey", "anotherValue"));
        assertThat(exception) //
                .hasMessage("Kaboom {\"args\": {\"myKey\": \"myValue\", \"anotherKey\": \"anotherValue\"}}") //
                .isInstanceOf(clazz);
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldFormatArgumentsAndException(Class<?> clazz) throws Exception {
        Exception cause = new Exception("initial stack");
        Exception exception = (Exception) clazz.getDeclaredConstructor(Throwable.class, String.class, Consumer.class)
                .newInstance(cause, "Kaboom", (Consumer<Arguments>) a -> a.add("myKey", "myValue").add("anotherKey", "anotherValue"));
        assertThat(exception) //
                .hasMessage("Kaboom {\"args\": {\"myKey\": \"myValue\", \"anotherKey\": \"anotherValue\"}}") //
                .isInstanceOf(clazz) //
                .hasCause(cause);
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldSerializeException(Class<?> clazz) throws Exception {
        Exception cause = new Exception("initial stack");
        Exception toSerialize = (Exception) clazz.getDeclaredConstructor(Throwable.class, String.class).newInstance(cause, "myMessage");
        Exception deserialized = serializationRoundTrip(toSerialize);

        assertThat(deserialized) //
                .hasMessage(toSerialize.getMessage()) //
                .isInstanceOf(clazz) //
                .hasCause(cause);
    }

    @Test(dataProvider = "exceptionProvider")
    public void shouldSerializeExceptionWithArgument(Class<?> clazz) throws Exception {
        Exception cause = new Exception("initial stack");
        UUID randomUUID = UUID.randomUUID();
        Exception toSerialize = (Exception) clazz.getDeclaredConstructor(Throwable.class, String.class, Consumer.class)
                .newInstance(cause, "myMessage", (Consumer<Arguments>) a -> a.add("myKey", "myValue").add("anotherKey", 42).add("lastKey", randomUUID));
        Exception deserialized = serializationRoundTrip(toSerialize);

        assertThat(deserialized) //
                .hasMessage(toSerialize.getMessage()) //
                .isInstanceOf(clazz) //
                .hasCause(cause);
    }

    private static Exception serializationRoundTrip(Exception toSerialize) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(toSerialize);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        return (Exception) objectInputStream.readObject();
    }
}
