package io.github.civitz.java.experiments.templater;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
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
    public void _10_args_averageCase_naiveRegex(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_naiveRegex(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_bigText_naiveRegex(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_someInTemplate_bigText_naiveRegex(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.naiveRegexReplace(torender.template, torender.values));
    }

    @Benchmark
    public void _10_args_averageCase_scrolling(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_scrolling(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_bigText_scrolling(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_someInTemplate_bigText_scrolling(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.scrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _10_args_averageCase_bufferedScrolling(AllMatched10AverageText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_bufferedScrolling(AllMatched100 torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_allInTemplate_bigText_bufferedScrolling(AllMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    @Benchmark
    public void _100_args_someInTemplate_bigText_bufferedScrolling(LittleMatched100BigText torender, Blackhole blackhole) {
        blackhole.consume(TemplateRenderers.bufferedScrolling(torender.template, torender.values));
    }

    public static void main(String[] args) throws Exception {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.BASIC_ISO_DATE)
                .appendLiteral('T')
                .appendValue(ChronoField.HOUR_OF_DAY)
                .appendValue(ChronoField.MINUTE_OF_HOUR)
                .appendValue(ChronoField.SECOND_OF_MINUTE)
                .toFormatter();
        Path basePath = Paths.get("benchmark-result").toAbsolutePath();
        Files.createDirectories(basePath);
        String result = basePath.resolve(LocalDateTime.now().format(format) + ".csv").toString();

        org.openjdk.jmh.runner.options.Options opt = new OptionsBuilder()
                .include(TemplateRendererBenchmark.class.getSimpleName())
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.CSV)
//                .result("./benchmark-result/" + System.currentTimeMillis() + ".csv")
                .result(result)
                .addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                .jvmArgsAppend("-Djmh.stack.period=1")
                .warmupIterations(5)
                .warmupTime(TimeValue.seconds(2L))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(10L))
                .timeUnit(TimeUnit.MICROSECONDS)
                .forks(2)
                .mode(Mode.AverageTime)
                .build();

        new Runner(opt).run();
//        org.openjdk.jmh.Main.main(args);
    }
}
