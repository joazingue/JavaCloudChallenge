package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;
import com.gbm.challenge.domains.Issuer;

public class ValidateStocks implements IBusinessRules {
	private GBMOrder order;
	private InvestmentAccount InvAccount;
	
	@Override
	public void SetOrder(GBMOrder validateOrder) {
		order = validateOrder;
	}

	/*
	 * Insufficient stocks: When selling stocks, you must have enough stock in order to fulfill it.
	 */
	@Override
	public Validation validate() {
		if(order.getOperation().equals(Operation.SELL)) {
			// Creates a new issuer from the order
			Issuer issuer = new Issuer();
			issuer.setIssuerName(order.getIssuer_name());
			issuer.setSharePrice(order.getShare_price());
			issuer.setTotalShares(order.getTotal_shares());
			// Search for the issuer in the account list
			int idx = InvAccount.getIssuers().indexOf(issuer);
			// Gets the actual issuer
			Issuer oldIssuer = InvAccount.getIssuers().get(idx);
			// substracts from the account the total shares
			Long totalShares = oldIssuer.getTotalShares() - issuer.getTotalShares();
			if(totalShares < 0) return Validation.INSUFFICIENT_STOCKS;
		}
		return Validation.CORRECT;
	}

	@Override
	public void SetAccount(InvestmentAccount account) {
		InvAccount = account;
	}

}
