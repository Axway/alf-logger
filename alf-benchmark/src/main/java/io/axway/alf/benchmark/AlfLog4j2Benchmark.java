package io.axway.alf.benchmark;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import io.axway.alf.log.Logger;
import io.axway.alf.log4j2.Log4j2LoggerFactory;

/**
 * This benchmark measures the overhead of using alf-log4j2 instead of directly log4j2
 */
@State(Scope.Benchmark)
public class AlfLog4j2Benchmark {
    private Logger m_alf;
    private org.apache.logging.log4j.Logger m_log4j2;

    @Setup
    public void setUp() {
        m_alf = new Log4j2LoggerFactory().getLogger(AlfLog4j2Benchmark.class);
        m_log4j2 = LogManager.getLogger(AlfLog4j2Benchmark.class);

        if (!m_log4j2.isInfoEnabled()) {
            throw new IllegalStateException("Info messages are disabled");
        } else if (m_log4j2.isTraceEnabled()) {
            throw new IllegalStateException("Trace messages are enabled");
        }
    }

    @TearDown
    public void tearDown() throws IOException {
        // Stop log4j2
        LogManager.shutdown();
        // Cleanup file before next round
        Files.deleteIfExists(Path.of("target/log4j2.log"));
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public void ignoreMessageWithArgumentsWithAlf(BenchmarkValueGenerator g) {
        m_alf.trace("This is the message", args -> args.add("first", g.stringValue()).add("second", g.longValue()));
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public void logMessageWithArgumentsWithAlf(BenchmarkValueGenerator g) {
        m_alf.info("This is the message", args -> args.add("first", g.stringValue()).add("second", g.longValue()));
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public void ignoreMessageWithArgumentsWithLog4j2(BenchmarkValueGenerator g) {
        m_log4j2.trace("This is the message {\"args\": {\"first\": \"{}\", \"second\": {}}}", g.stringValue(), g.longValue());
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public void logMessageWithArgumentsWithLog4j2(BenchmarkValueGenerator g) {
        m_log4j2.info("This is the message {\"args\": {\"first\": \"{}\", \"second\": {}}}", g.stringValue(), g.longValue());
    }
}
