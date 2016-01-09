package io.github.civitz.java.experiments.immutables;

/**
 * An immutable class, with public final attributes.
 * <p>
 * Maybe not suitable for purists, but it is indeed immutable, so why not...
 *
 */
public class PublicAttributes {
	public final String name;
	public final String address;
	public final int age;
	
	// This could be public
	private PublicAttributes(String name, String address, int age) {
		this.name = name;
		this.address = address;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "PublicAttributes [name=" + name + ", address=" + address + ", age=" + age + "]";
	}

	public static Builder create(){
		return new Builder();
	}
	
	public static class Builder{
		// we can specify default values for attributes
		private String name;
		private String address;
		private int age;
		
		public Builder withName(String name){
			this.name = name;
			return this;
		}
		
		public Builder withAddress(String address){
			this.address = address;
			return this;
		}
		
		public Builder withAge(int age){
			this.age = age;
			return this;
		}
		
		public PublicAttributes build(){
			return new PublicAttributes(name, address, age);
		}
	}
}
