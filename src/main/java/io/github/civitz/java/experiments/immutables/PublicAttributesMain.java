package io.github.civitz.java.experiments.immutables;

import io.github.civitz.java.experiments.immutables.PublicAttributes.Builder;

public class PublicAttributesMain {
	public static void main(String[] args) {
		Builder builder = PublicAttributes.create();
		builder.withName("John");
		builder.withAddress("Sesame street");
		builder.withAge(5);
		PublicAttributes pa = builder.build();
		
		System.out.println(pa.name); // direct access 
		// John
		System.out.println(pa);
		// PublicAttributes [name=John, address=Sesame street, age=5]
	}
}
