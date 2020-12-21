package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

public class ValidateDuplication implements IBusinessRules {
	// TODO
	private GBMOrder order;
	private InvestmentAccount InvAccount;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	@Override
	public Validation validate() {
		System.out.println(order);
		System.out.println(InvAccount);
		return Validation.CORRECT;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;		
	}
}
