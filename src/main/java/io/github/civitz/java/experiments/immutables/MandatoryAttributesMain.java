package io.github.civitz.java.experiments.immutables;

import io.github.civitz.java.experiments.immutables.MandatoryAttributes.MandatoryAttributes_address;
import io.github.civitz.java.experiments.immutables.MandatoryAttributes.MandatoryAttributes_name;
import io.github.civitz.java.experiments.immutables.MandatoryAttributes.MandatoryAttributes_optional;

public class MandatoryAttributesMain {
	public static void main(String[] args) {
		MandatoryAttributes_name firstBuilder = MandatoryAttributes.create();
		MandatoryAttributes_address secondBuilder = firstBuilder.withName("John");
		MandatoryAttributes_optional thirdBuilder = secondBuilder.withAddress("Sesame Street");
		
		MandatoryAttributes bc1 = thirdBuilder.build();
		thirdBuilder.withAge(5);
		MandatoryAttributes bc2 = thirdBuilder.build();
		thirdBuilder.withGame("Zelda");
		MandatoryAttributes bc3 = thirdBuilder.build();
		
		System.out.println(bc1); 
//		MandatoryAttributes [name=John, address=Sesame Street, age=Optional.empty, game=Optional.empty]

		System.out.println(bc2); 
//		MandatoryAttributes [name=John, address=Sesame Street, age=Optional[5], game=Optional.empty]
		
		System.out.println(bc3); 
//		MandatoryAttributes [name=John, address=Sesame Street, age=Optional[5], game=Optional[Zelda]]
	}
}
