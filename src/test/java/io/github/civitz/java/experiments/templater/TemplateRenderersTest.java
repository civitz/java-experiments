package io.github.civitz.java.experiments.templater;

import io.vavr.Function2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class TemplateRenderersTest {

    static abstract class Renderer{
        abstract String render(String template, Map<String, String> values);
    }

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

    private static Stream<Arguments> renderers() {
        return Stream.of(
                Arguments.of(renderer(TemplateRenderers::naiveRegexReplace, "naive")),
                Arguments.of(renderer(TemplateRenderers::scrolling, "scrolling")),
                Arguments.of(renderer(TemplateRenderers::bufferedScrolling, "bufferedScrolling"))
        );
    }

    @ParameterizedTest
    @MethodSource("renderers")
    public void shouldRenderSimpleTemplate(Renderer templater) {
        assertThat(templater.render("[salutation] world", HashMap.of("salutation", "hello")))
                .isEqualTo("hello world");
    }

    @ParameterizedTest
    @MethodSource("renderers")
    public void shouldRenderTemplateWithMoreValuesAndSomeUnmatchedValuesInTemplate(Renderer templater) {
        assertThat(templater.render("[salutation] [adjective ] world[ending]", HashMap.of("salutation", "hello", "adjective", "dear")))
                .isEqualTo("hello dear world[ending]");
    }


    @ParameterizedTest
    @MethodSource("renderers")
    public void shouldRenderTemplateWithMoreValuesAndSomeExtraValues(Renderer templater) {
        assertThat(templater.render("[salutation] world", HashMap.of("salutation", "hello", "adjective", "dear")))
                .isEqualTo("hello world");
    }

    @ParameterizedTest
    @MethodSource("renderers")
    public void shouldRenderBadTemplate(Renderer templater) {
        assertThat(templater.render("[s[s[salutation]] world", HashMap.of("salutation", "hello")))
                .isEqualTo("[s[shello] world");
    }
}