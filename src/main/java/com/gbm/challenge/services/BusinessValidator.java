package com.gbm.challenge.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;
import com.gbm.challenge.domains.Issuer;
import com.gbm.challenge.domains.StockOperation;
import com.gbm.challenge.services.rules.IBusinessRules;
import com.gbm.challenge.services.rules.Operation;
import com.gbm.challenge.services.rules.Validation;

// Can implement Abstract factory or Rule addition
@Service
public class BusinessValidator {

	private StockOperation stock;
	private GBMOrder order;
	private Validation validation = Validation.INVALID_OPERATION;
	private List<IBusinessRules> businessRules = new ArrayList<IBusinessRules>();
	
	
	public void setStockOperation(StockOperation stock) {
		this.stock = stock;
	}
	public void setOrder(GBMOrder order) {
		this.order = order;
	}
	public void AddBusinessRule(IBusinessRules busRule) {
		businessRules.add(busRule);
	}
	public List<IBusinessRules> getBusinessRules() {
		return businessRules;
	}
	private void sellOrder() {
		// TODO
	}
	private void buyOrder() {
		// TODO
	}
	public StockOperation Validate() {
		// Validate each business rule
		for (IBusinessRules iBusinessRules : businessRules) {
			// Set the GBM order for the rule
			iBusinessRules.SetOrder(order);
			// Get the validation
			validation = iBusinessRules.validate();
			// If validation is not CORRECT, returns bussiness error
			if(!validation.equals(Validation.CORRECT)) {
				stock.addBusinessError(validation);
				// Not sure if issuers should be removed or stay as it was
				// stock.getCurrentBalance().removeAllIssuers();
				return stock;
			}
		}
		
		if(order.getOperation().equals(Operation.BUY)) {
			buyOrder();
		}
		if(order.getOperation().equals(Operation.SELL)) {
			sellOrder();
		}
		
		
		
//		InvestmentAccount invAcc = stock.getCurrentBalance();
//		List<Issuer> listIssuers = invAcc.getIssuers();
//		if(listIssuers.contains(order.getIssuerName())) {
//			for (Issuer issuer : ) {
//				
//			}
//		} else {
//			Issuer newIssuer = new Issuer();
//			newIssuer.setIssuerName(order.getIssuerName());
//			newIssuer.setSharePrice(order.getSharePrice());
//			newIssuer.setTotalShares(order.getTotalShares());
//			
//			
//		}
		
		
		
		
		return stock;
	}
}
