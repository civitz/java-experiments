package io.github.civitz.java.experiments.monads.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeImmutable = "*")
public interface AbstractProductInfo {
	AbstractProduct getProduct();
	Cash getCost();
}