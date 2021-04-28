package io.axway.alf.benchmark;

import java.util.concurrent.*;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import static java.util.UUID.*;

/**
 * This class generates values for the benchmarks.
 * Consecutive calls need to return different values to avoid being inlined by the JVM but uses a cache to quickly return values.
 */
@State(Scope.Thread)
public class BenchmarkValueGenerator {
    private static final int CACHE_VALUES_COUNT = 64;  // Needs to be a power of 2
    private static final int CACHE_MASK = CACHE_VALUES_COUNT - 1; // Using mask and not modulo to avoid negative values when index overflows
    private static final char[] SPECIAL_CHARS = {'"', '\\', '\r', '\n', '\t', 0, 1, 2, 3, 4};

    private long m_long;
    private final String[] m_stringValues;
    private final String[] m_stringsWithSpecialsChars;
    private int m_index;

    public BenchmarkValueGenerator() {
        m_long = ThreadLocalRandom.current().nextLong();
        m_stringValues = new String[CACHE_VALUES_COUNT];
        m_stringsWithSpecialsChars = new String[CACHE_VALUES_COUNT];
        for (int i = 0; i < CACHE_VALUES_COUNT; i++) {
            m_stringValues[i] = randomUUID().toString();
            m_stringsWithSpecialsChars[i] = m_stringValues[i].replace('-', SPECIAL_CHARS[i % SPECIAL_CHARS.length]);
        }
    }

    public long longValue() {
        return m_long += nextIndex();
    }

    public String stringValue() {
        return m_stringValues[nextIndex()];
    }

    public String stringWithSpecialChars() {
        return m_stringsWithSpecialsChars[nextIndex()];
    }

    private int nextIndex() {
        return ++m_index & CACHE_MASK;
    }
}
