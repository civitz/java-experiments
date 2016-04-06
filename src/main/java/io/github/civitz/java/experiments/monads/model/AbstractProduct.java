package io.github.civitz.java.experiments.monads.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeImmutable = "*")
public interface AbstractProduct {
	@Value.Parameter
	String getName();
}
