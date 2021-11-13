package io.github.civitz.java.experiments.templater;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class TemplatersBenchmark {
    @State(Scope.Benchmark)
    public static class AllMatched100 {
        public String template;
        public Map<String, String> values;
        public AllMatched100(){
            values = Stream.range(0,100)
                    .toMap(i-> Tuple.of("key"+i,"value"+i));
            template = values.map(Tuple2::_1).mkString("[","][","]");
        }
    }

    @State(Scope.Benchmark)
    public static class AllMatched100BigText {
        public String template;
        public Map<String, String> values;
        public AllMatched100BigText(){
            values = Stream.range(0,100)
                    .toMap(i-> Tuple.of("key"+i,"value"+i));
            String bigGap = Stream.range(0, 10000)
                    .map(ign->"rand")
                    .mkString("");
            template = values.map(Tuple2::_1).mkString("[","]"+bigGap+"[","]");
        }
    }

    @State(Scope.Benchmark)
    public static class LittleMatched100BigText {
        public String template;
        public Map<String, String> values;
        public LittleMatched100BigText(){
            values = Stream.range(0,100)
                    .toMap(i-> Tuple.of("key"+i,"value"+i));
            String bigGap = Stream.range(0, 10000)
                    .map(ign->"rand")
                    .mkString("");
            template = values.map(Tuple2::_1)
                    .grouped(10)
                    .map(group->group.get())
                    .mkString("[","]"+bigGap+"[","]");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_naiveRegex(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(Templaters.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_naiveRegex(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_naiveRegex(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_scrolling(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(Templaters.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_scrolling(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_scrolling(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_buffered(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(Templaters.buffered(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_buffered(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.buffered(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_buffered(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(Templaters.buffered(torender.template, torender.values));
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new AllMatched100BigText().template.length());
        org.openjdk.jmh.Main.main(args);
    }
}
