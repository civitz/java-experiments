package io.github.civitz.java.experiments.immutables;

import io.github.civitz.java.experiments.immutables.ClassicImmutable.Builder;

public class ClassicImmutableMain {

	public static void main(String[] args) {
		// a builder of type ClassicImmutable.Builder
		final Builder builder = ClassicImmutable.create();
		builder.withName("John");
		builder.withAddress("Sesame street");
		builder.withAge(5);
		final ClassicImmutable cb = builder.build();

		System.out.println(cb.getName()); // John
		System.out.println(cb); 
		// ClassicImmutable [name=John, address=Sesame street, age=5]
	}

}
