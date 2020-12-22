package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

/**
 * Interface class to create business rules
 */
public interface IBusinessRules {

	void SetOrder(GBMOrder validateOrder);
	void SetAccount(InvestmentAccount account);
	Validation validate();
	
}
