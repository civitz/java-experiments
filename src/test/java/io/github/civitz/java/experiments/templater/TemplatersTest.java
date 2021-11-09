package io.github.civitz.java.experiments.templater;

import io.vavr.Function2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class TemplatersTest {
    static Renderer renderer(Function2<String, Map<String, String>, String> f, String name){
        return new Renderer() {
            @Override
            String render(String template, Map<String, String> values) {
                return f.apply(template,values);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }
    static abstract class Renderer{
        abstract String render(String template, Map<String, String> values);
    }
    private static Stream<Arguments> templaters() {
        return Stream.of(
                Arguments.of(renderer(Templaters::naiveRegexReplace, "naive")),
                Arguments.of(renderer(Templaters::scrolling, "scrolling"))
        );
    }

    @ParameterizedTest
    @MethodSource("templaters")
    public void shouldRenderSimpleTemplate(Renderer templater) {
        assertThat(templater.render("[ciao] lol", HashMap.of("ciao", "miao")))
                .isEqualTo("miao lol");
    }

    @ParameterizedTest
    @MethodSource("templaters")
    public void shouldRenderTemplateWithMoreValuesAndSomeUnmatchedValuesInTemplate(Renderer templater) {
        assertThat(templater.render("[ciao] [mondo ] lol [qualcosa]", HashMap.of("ciao", "miao", "mondo", "brrr")))
                .isEqualTo("miao brrr lol [qualcosa]");
    }


    @ParameterizedTest
    @MethodSource("templaters")
    public void shouldRenderTemplateWithMoreValuesAndSomeExtraValues(Renderer templater) {
        assertThat(templater.render("[ciao] lol", HashMap.of("ciao", "miao", "mondo", "brrr")))
                .isEqualTo("miao lol");
    }
}