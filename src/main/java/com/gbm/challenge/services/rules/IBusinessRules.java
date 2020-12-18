package com.gbm.challenge.services.rules;

import com.gbm.challenge.domains.GBMOrder;

// Method factory
// Abstract Factory
// Builder
public interface IBusinessRules {

	void SetOrder(GBMOrder validateOrder);
	Validation validate();
	
}
