package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;

public class ValidateBalance implements IBusinessRules {

	private GBMOrder order;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	@Override
	public Validation validate() {
		System.out.println(order);
		return Validation.CORRECT;
	}


}
