package io.axway.alf.log4j2.layout;

import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.time.MutableInstant;
import org.apache.logging.log4j.message.SimpleMessage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlfJsonLayoutTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    @Test
    public void shouldFormatTime() {
        long now = System.currentTimeMillis();

        // Given a AlfJsonLayout
        AlfJsonLayout layout = AlfJsonLayout.newBuilder()
                .withDateFormat(DATE_FORMAT)
                .withThreadPrinting(false)
                .withLevelPrinting(false)
                .withLoggerPrinting(false)
                .build();

        // And a log event
        MutableInstant mutableInstant = new MutableInstant();
        mutableInstant.initFromEpochMilli(now, 0);
        Log4jLogEvent event = Log4jLogEvent.newBuilder().setMessage(new SimpleMessage("Just testing")).setInstant(mutableInstant).build();

        // When formatting the event
        String output = layout.toSerializable(event);

        // Then it should be the expected one (using SimpleDateFormat to ensure compatibility)
        String expected = "{\"time\": \"" + new SimpleDateFormat(DATE_FORMAT).format(new Date(now)) + "\", \"message\": \"Just testing\"}";
        assertThat(output).isEqualToIgnoringNewLines(expected);
    }
}
