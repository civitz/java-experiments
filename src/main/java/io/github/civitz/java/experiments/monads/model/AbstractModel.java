package io.github.civitz.java.experiments.monads.model;

import java.util.List;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeImmutable = "*")
public interface AbstractModel {
	List<AbstractProductInfo> getProducts();
	Cash getAllowance();
	String getStatus();
}