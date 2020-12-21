
package com.gbm.challenge.services.rules;

import java.util.Calendar;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

public class ValidateMarket implements IBusinessRules{
	
	private GBMOrder order;
	private InvestmentAccount InvAccount;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	/*
	 * Closed market: All operations must happen between 6am and 3pm.
	 */
	@Override
	public Validation validate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(order.getTimestamp() * 1000);
		if(cal.get(Calendar.HOUR_OF_DAY) >=6 & cal.get(Calendar.HOUR_OF_DAY) < 15) return Validation.CORRECT;
		return Validation.CLOSED_MARKET;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;
	}
}
