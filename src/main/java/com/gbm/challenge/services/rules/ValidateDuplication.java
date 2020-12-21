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

	/*
	 * Duplicated operation: No operations for the same amount must happen within a 5 minutes interval, 
	 * as they are considered duplicates.
	 */
	@Override
	public Validation validate() {
		boolean match = InvAccount.getOrders().stream().anyMatch(ord -> (
				Math.abs(ord.getTimestamp() - order.getTimestamp()) <= 300000 &
				ord.getOperation().equals(order.getOperation()) & 
				ord.getTotalSharesPrice().equals(order.getTotalSharesPrice())));
		if(match) return Validation.DUPLICATED_OPERATION;
		return Validation.CORRECT;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;		
	}
}
