
package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

public class ValidateOrder implements IBusinessRules{
	// TODO
	private GBMOrder order;
	private InvestmentAccount InvAccount;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	/*
	 * Invalid Operation: Any other invalidity must be prevented.
	 */
	@Override
	public Validation validate() {
		if(order.getIssuer_name() == null) return Validation.INVALID_OPERATION;
		if(order.getOperation() == null) return Validation.INVALID_OPERATION;
		if(order.getShare_price() == null) return Validation.INVALID_OPERATION;
		if(order.getTimestamp() == null) return Validation.INVALID_OPERATION;
		if(order.getTotal_shares() == null) return Validation.INVALID_OPERATION;
		return Validation.CORRECT;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;
	}
}
