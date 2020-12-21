package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

public class ValidateBalance implements IBusinessRules {
	
	private GBMOrder order;
	private InvestmentAccount InvAccount;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	/*
	 * Insufficient balance: When buying stocks, you must have enough cash in order to fulfill it.
	 */
	@Override
	public Validation validate() {
		if(order.getOperation().equals(Operation.BUY)) {
			Double cash = InvAccount.getCash() - order.getTotalSharesPrice();
			if(cash < 0) return Validation.INSUFFICIENT_BALANCE;
		}
		return Validation.CORRECT;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;
	}
}
