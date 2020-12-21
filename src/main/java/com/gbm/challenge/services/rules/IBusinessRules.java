package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;

// Method factory
// Abstract Factory
// Builder
public interface IBusinessRules {

	void SetOrder(GBMOrder validateOrder);
	void SetAccount(InvestmentAccount account);
	Validation validate();
	
}
