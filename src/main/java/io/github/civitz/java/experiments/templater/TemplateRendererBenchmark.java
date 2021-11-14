package io.github.civitz.java.experiments.templater;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class TemplateRendererBenchmark {
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

    /**
     * This state represents an average 3.5kb mail with 10 template parameters
     */
    @State(Scope.Benchmark)
    public static class AllMatched10AverageText {
        public String template;
        public Map<String, String> values;

        public AllMatched10AverageText() {
            values = Stream.range(0, 10)
                    .toMap(i -> Tuple.of("key" + i, "value" + i));
            String mediumGap = Stream.range(0, 100)
                    .map(ign -> "rand")
                    .mkString("");
            template = values.map(Tuple2::_1).mkString("[","]"+mediumGap+"[","]");
        }
    }

    @State(Scope.Benchmark)
    public static class AllMatched100BigText {
        public String template;
        public Map<String, String> values;

        public AllMatched100BigText() {
            values = Stream.range(0, 100)
                    .toMap(i -> Tuple.of("key" + i, "value" + i));
            String bigGap = Stream.range(0, 10000)
                    .map(ign -> "rand")
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
                    .map(Traversable::get)
                    .mkString("[","]"+bigGap+"[","]");
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _10_args_averageCase_naiveRegex(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_naiveRegex(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_naiveRegex(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_naiveRegex(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _10_args_averageCase_scrolling(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_scrolling(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_scrolling(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_scrolling(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _10_args_averageCase_bufferedScrolling(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bufferedScrolling(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_allInTemplate_bigText_bufferedScrolling(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
//    @Fork(value = 1, warmups = 2)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    @Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//    @Measurement(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void _100_args_someInTemplate_bigText_bufferedScrolling(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
