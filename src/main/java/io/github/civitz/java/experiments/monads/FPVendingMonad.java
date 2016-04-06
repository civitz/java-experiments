package io.github.civitz.java.experiments.monads;

import java.util.function.Function;

import io.github.civitz.java.experiments.monads.model.AbstractProduct;
import io.github.civitz.java.experiments.monads.model.Cash;
import io.github.civitz.java.experiments.monads.model.Model;
import io.github.civitz.java.experiments.monads.model.Product;
import io.github.civitz.java.experiments.monads.model.ProductInfo;

/**
 * A monad modeling a functional-style vending-machine.
 * <p>
 * Based on a conference talk by JUG Milano, Mario Fusco, and Luca Molteni.
 * @see <a href="https://www.youtube.com/watch?v=xJUYas0FPNI">YouTube video here</a>
 */
@FunctionalInterface
public interface FPVendingMonad {
	Model startingFrom(Model model);
	
	default FPVendingMonad compose(Function<Model, Model> f){
		return model-> f.apply(startingFrom(model));
	}
	
	default FPVendingMonad cash(Cash cash){
		return compose(model -> FPVendingMachine.addCash(model, cash));
	}
	
	default FPVendingMonad choose(AbstractProduct product){
		return compose(model -> FPVendingMachine.chooseProduct(model, product));
	}
}

class Main{
	public static void main(String[] args) {
		FPVendingMonad monad = model -> model; 
		Model initialModel = Model.builder()
			.addProducts(ProductInfo.of(Product.of("coffee"), Cash.of(10)))
			.allowance(Cash.of(0))
			.status("Ready!").build();
		
		Model finalModel = monad.cash(Cash.of(5))
								.cash(Cash.of(6))
								.cash(Cash.of(3))
								.choose(Product.of("coffee"))
								.startingFrom(initialModel);
		System.out.println(finalModel);
					
	}
}