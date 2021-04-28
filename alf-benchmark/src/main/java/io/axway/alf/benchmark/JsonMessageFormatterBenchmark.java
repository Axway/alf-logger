package io.axway.alf.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import io.axway.alf.formatter.JsonMessageFormatter;

import static io.axway.alf.formatter.JsonMessageFormatter.getFormatter;

/**
 * This benchmark measures the time it takes to format log messages
 */
@State(Scope.Benchmark)
public class JsonMessageFormatterBenchmark {
    private JsonMessageFormatter m_formatter;

    @Setup
    public void setUp() {
        m_formatter = getFormatter();
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public String writeMessageWithTwoStrings(BenchmarkValueGenerator g) {
        return m_formatter.format("This is the message", args -> args.add("first", g.stringValue()).add("second", g.stringValue()));
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public String writeMessageWithTwoLongs(BenchmarkValueGenerator g) {
        return m_formatter.format("This is the message", args -> args.add("first", g.longValue()).add("second", g.longValue()));
    }

    @Benchmark
    @Fork(value = 3)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    public String writeMessageWithSpecialChars(BenchmarkValueGenerator g) {
        return m_formatter.format("This is the message", args -> args.add("first", g.stringWithSpecialChars()).add("second", g.stringWithSpecialChars()));
    }
}
