package com.gbm.challenge.services;

import java.util.ArrayList;
import java.util.List;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.Stock;
import com.gbm.challenge.services.rules.IBusinessRules;
import com.gbm.challenge.services.rules.Validation;

// Can implement Abstract factory
public class BusinessValidator {

	private Stock stock;
	private GBMOrder order;
	private Validation validation = Validation.INVALID_OPERATION;
	private List<IBusinessRules> businessRules = new ArrayList<IBusinessRules>();
	
	
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public void setOrder(GBMOrder order) {
		this.order = order;
	}
	public void AddBusinessRule(IBusinessRules busRule) {
		businessRules.add(busRule);
	}
	public Stock Validate() {
		for (IBusinessRules iBusinessRules : businessRules) {
			validation = iBusinessRules.validate();
			if(!validation.equals(Validation.CORRECT)) {
				stock.addBusinessError(validation.toString());
				break;
			}
		}
		return stock;
	}
}
