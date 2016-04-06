package io.github.civitz.java.experiments.monads;

import java.util.function.Function;

import io.github.civitz.java.experiments.monads.model.Cash;
import io.github.civitz.java.experiments.monads.model.Model;
import io.github.civitz.java.experiments.monads.model.Product;
import io.github.civitz.java.experiments.monads.model.AbstractProduct;import io.github.civitz.java.experiments.monads.model.ProductInfo;

/**
 * A monad modeling a functional-style vending-machine.
 * <p>
 * Based on a conference talk by JUG Milano, Mario Fusco, and Luca Molteni.
 * @author roberto
 * @see <a href="https://www.youtube.com/watch?v=xJUYas0FPNI">YouTube video here</a>
 */
@FunctionalInterface
public interface FPVendingMonad {
	Model startingFrom(Model model);
	
	default FPVendingMonad map(Function<Model, Model> f){
		return model-> f.apply(startingFrom(model));
	}
	
	default FPVendingMonad cash(Cash cash){
		return map(model -> FPVendingMachine.addCash(model, cash));
	}
	
	default FPVendingMonad choose(AbstractProduct product){
		return map(model -> FPVendingMachine.chooseProduct(model, product));
	}
}

class MyFpVendingMonad{
	public static void main(String[] args) {
	FPVendingMonad monad = model -> model; 
		Model initialModel = Model.builder()
			.addProducts(ProductInfo.builder()
					.cost(someCash(10))
					.product(someProduct("coffee"))
					.build())
			.allowance(Cash.builder().amount(0).build())
			.status("Ready!").build();
		
		Model finalModel = monad.cash(someCash(5))
				.cash(someCash(6))
				.cash(someCash(3))
			.choose(someProduct("coffee"))
			.startingFrom(initialModel);
		
		System.out.println(finalModel);
					
	}

	private static Product someProduct(String string) {
		return Product.builder().name(string).build();
	}

	private static Cash someCash(final int amount) {
		return Cash.builder().amount(amount).build();
	}
}