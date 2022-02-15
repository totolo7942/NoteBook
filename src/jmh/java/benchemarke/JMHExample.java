package benchemarke;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHExample {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        static String[] array;
        static {
            array = new String[1000000];
            Arrays.fill(array, "AbabagalamagA");
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> sequential(BenchmarkState state) {
        return
                Arrays.stream(state.array)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public List<String> parallel(BenchmarkState state) {
        return
                Arrays.stream(state.array)
                        .parallel()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHExample.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
