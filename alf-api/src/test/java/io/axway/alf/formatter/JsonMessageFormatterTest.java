package io.axway.alf.formatter;

import org.testng.annotations.Test;

import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;
import static java.util.Collections.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonMessageFormatterTest {
    @Test
    public void testFormatWithStringAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myKey", "myValue"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myKey\": \"myValue\"}}");
    }

    @Test
    public void testFormatWithIntAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myKey", 42));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myKey\": 42}}");
    }

    @Test
    public void testFormatWithIntegerAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myKey", new Integer(42)));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myKey\": 42}}");
    }

    @Test
    public void testFormatWithListOfIntegerAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myList", range(0, 5).boxed().collect(toList())));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myList\": [0, 1, 2, 3, 4]}}");
    }

    @Test
    public void testFormatWithListOfStringAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myList", range(0, 5).mapToObj(i -> "value_" + i).collect(toList())));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myList\": [\"value_0\", \"value_1\", \"value_2\", \"value_3\", \"value_4\"]}}");
    }

    @Test
    public void testFormatWithMapAsArguments() {
        String message = getFormatter().format("myMessage", a -> a.add("nested", singletonMap("key", "value")));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"nested\": {\"key\": \"value\"}}}");
    }

    @Test
    public void testFormatWithNestedArguments() {
        String message = getFormatter().format("myMessage", a -> a.add("nested", a2 -> a2.add("key", "value")));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"nested\": {\"key\": \"value\"}}}");
    }

    @Test
    public void testEscapeStringWithBackspace() {
        String message = getFormatter().format("myMessage", a -> a.add("backspace", "string\bstring"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"backspace\": \"string\\bstring\"}}");
    }

    @Test
    public void testEscapeStringWithTab() {
        String message = getFormatter().format("myMessage", a -> a.add("tab", "string\tstring"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"tab\": \"string\\tstring\"}}");
    }

    @Test
    public void testEscapeStringWithNewLine() {
        String message = getFormatter().format("myMessage", a -> a.add("newLine", "string\nstring"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"newLine\": \"string\\nstring\"}}");
    }

    @Test
    public void testEscapeStringWithCarriageReturn() {
        String message = getFormatter().format("myMessage", a -> a.add("carriageReturn", "string\rstring"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"carriageReturn\": \"string\\rstring\"}}");
    }

    @Test
    public void testEscapeStringWithPageBreak() {
        String message = getFormatter().format("myMessage", a -> a.add("pageBreak", "string\fstring"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"pageBreak\": \"string\\fstring\"}}");
    }

    @Test
    public void testEscapeStringWithUndisplayedChar() {
        String message = getFormatter().format("myMessage", a -> a.add("undisplayedChar", "string" + ((char) 1) + "string"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"undisplayedChar\": \"string\\u0001string\"}}");
    }

    @Test
    public void testFormatWithNullAsArgument() {
        String message = getFormatter().format("myMessage", a -> a.add("myKey", null));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"myKey\": null}}");
    }

    @Test
    public void testFormatThrowableOnly() {
        String message = getFormatter().format("myMessage", new IllegalStateException("Kaboom"));
        assertThat(message).isEqualTo("myMessage {\"exception\": {\"type\": \"java.lang.IllegalStateException\", \"message\": \"Kaboom\"}}");
    }

    @Test
    public void testFormatAll() {
        String message = getFormatter().format("myMessage", a -> a.add("string", "foo").add("int", 42).add("nested", a2 -> a2.add("key", "value")),
                                               new IllegalStateException("Kaboom"));
        assertThat(message).isEqualTo("myMessage {\"args\": {\"string\": \"foo\", \"int\": 42, \"nested\": {\"key\": \"value\"}}, "
                                              + "\"exception\": {\"type\": \"java.lang.IllegalStateException\", \"message\": \"Kaboom\"}}");
    }
}
