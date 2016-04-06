package io.github.civitz.java.experiments.monads.model;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeImmutable = "*")
public abstract class AbstractCash {

	public abstract int getAmount();
	
	public Optional<Cash> minus(int amount) {
		if (this.getAmount() >= amount) {
			return Optional.of(Cash.builder().amount(this.getAmount() - amount).build());
		}
		return Optional.empty();
	}

	public Cash plus(int amount) {
		return Cash.builder().amount(this.getAmount() + amount).build();
	}

	
}
