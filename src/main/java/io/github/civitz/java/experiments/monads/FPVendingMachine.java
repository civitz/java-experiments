package io.github.civitz.java.experiments.monads;

import java.util.Optional;

import io.github.civitz.java.experiments.monads.model.Cash;
import io.github.civitz.java.experiments.monads.model.Model;
import io.github.civitz.java.experiments.monads.model.AbstractProduct;
import io.github.civitz.java.experiments.monads.model.AbstractProductInfo;

public class FPVendingMachine {
	public static Model addCash(Model model, Cash cash){
		return Model.copyOf(model)
				.withAllowance(model.getAllowance().plus(cash.getAmount()))
				.withStatus("Thanks!");
	}
	
	public static Model chooseProduct(Model model, AbstractProduct product){
		Optional<AbstractProductInfo> maybe = model.getProducts().stream()
			.filter(info->info.getProduct().equals(product))
			.findAny();
		
		return maybe.map(info->{
			
			Optional<Cash> caIHaz = model.getAllowance().minus(info.getCost().getAmount());
			return caIHaz.map(newCash->Model.copyOf(model).withAllowance(newCash).withStatus("Enjoy!"))
					.orElseGet(()-> Model.copyOf(model).withStatus("Insufficient Cash"));
		}).orElseGet(()-> Model.copyOf(model).withStatus("Product not found"));
	}
}
