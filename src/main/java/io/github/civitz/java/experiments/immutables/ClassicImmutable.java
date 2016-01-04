package io.github.civitz.java.experiments.immutables;

/**
 * A classic immutable class with getters and a builder. 
 * <p>
 * Nothing new here...
 */
public class ClassicImmutable {
	private final String name;
	private final String address;
	private final int age;
	
	private ClassicImmutable(String name, String address, int age) {
		this.name = name;
		this.address = address;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getAge() {
		return age;
	}
	
	@Override
	public String toString() {
		return "ClassicImmutable [name=" + name + ", address=" + address + ", age=" + age + "]";
	}

	public static Builder create(){
		return new Builder();
	}
	
	public static class Builder{
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
		
		public ClassicImmutable build(){
			return new ClassicImmutable(name, address, age);
		}
	}
}
