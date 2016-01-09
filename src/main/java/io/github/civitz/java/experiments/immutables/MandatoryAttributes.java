package io.github.civitz.java.experiments.immutables;

import java.util.Optional;

/**
 * An immutable class that has a constrained builder.
 * <p>
 * The builder forces you to set the mandatory attributes before letting you
 * call the {@link Builder#build()} method.
 * 
 */
public class MandatoryAttributes {
	private final String name;
	private final String address;
	private final Optional<Integer> age;
	private final Optional<String> game;

	private MandatoryAttributes(String name, String address, Optional<Integer> age, Optional<String> game) {
		this.name = name;
		this.address = address;
		this.age = age;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Optional<Integer> getAge() {
		return age;
	}

	public Optional<String> getGame() {
		return game;
	}

	@Override
	public String toString() {
		return "MandatoryAttributes [name=" + name + ", address=" + address + ", age=" + age + ", game=" + game + "]";
	}

	/**
	 * Let you create an object via a builder
	 * 
	 * @return a builder for {@link MandatoryAttributes} class.
	 */
	public static MandatoryAttributes_name create() {
		return new Builder();
	}

	// The first interface forces you to enter the name;
	// then it chains to a second interface
	// no chances to obtain an instance of the real object here
	public interface MandatoryAttributes_name {
		MandatoryAttributes_address withName(String name);
	}

	// The second interface forces you to enter address, then leads to the
	// optional part
	// still no chances to obtain an object
	public interface MandatoryAttributes_address {
		MandatoryAttributes_optional withAddress(String address);
	}

	// this interface contains all the optional setter and the build() method.
	// you can call build() immediately, or fill the needed fields with the
	// setter methods.
	public interface MandatoryAttributes_optional {
		MandatoryAttributes_optional withAge(Integer age);

		MandatoryAttributes_optional withGame(String game);

		// get the real object
		MandatoryAttributes build();
	}

	/**
	 * Builder for MandatoryAttributes class
	 * <p>
	 * The real builder implements all those interfaces at once. The only thing
	 * that forces the programmer to fill the attributes in the correct order,
	 * while respecting mandatory fields, is the interface chain. Note that this
	 * time the builder concrete class can be private, since we already share
	 * the interfaces as public.
	 * <p>
	 * Also worth noting: interfaces aside, this builder looks no different than
	 * classic builder classes.
	 */
	private static class Builder
			implements MandatoryAttributes_address, MandatoryAttributes_name, MandatoryAttributes_optional {

		private String name;
		private String address;
		private Optional<Integer> age = Optional.empty();
		private Optional<String> game = Optional.empty();

		private Builder() {
		}

		@Override // from MandatoryAttributes_optional
		public MandatoryAttributes_optional withAge(Integer age) {
			this.age = Optional.ofNullable(age);
			return this;
		}

		@Override // from MandatoryAttributes_optional
		public MandatoryAttributes_optional withGame(String game) {
			this.game = Optional.ofNullable(game);
			return this;
		}

		@Override // from MandatoryAttributes_name
		public MandatoryAttributes_address withName(String name) {
			this.name = name;
			return this;
		}

		@Override // from MandatoryAttributes_address
		public MandatoryAttributes_optional withAddress(String address) {
			this.address = address;
			return this;
		}

		@Override // from MandatoryAttributes_optional
		public MandatoryAttributes build() {
			return new MandatoryAttributes(name, address, age, game);
		}
	}

}
