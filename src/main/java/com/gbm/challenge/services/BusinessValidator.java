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
		// Gets the account to work with
		InvestmentAccount invAcc = stock.getCurrentBalance();
		// Gets the cash form the operation
		Double cash = invAcc.getCash() + order.getTotalSharesPrice();
		// Sets new balance
		invAcc.setCash(cash);
		// Creates a new issuer from the order
		Issuer issuer = new Issuer();
		issuer.setIssuerName(order.getIssuerName());
		issuer.setSharePrice(order.getSharePrice());
		issuer.setTotalShares(order.getTotalShares());
		// Search for the issuer in the account list
		int idx = invAcc.getIssuers().indexOf(issuer);
		// Gets the actual issuer
		Issuer oldIssuer = invAcc.getIssuers().get(idx);
		// substracts from the account the total shares
		Long totalShares = oldIssuer.getTotalShares() - issuer.getTotalShares();
		// Removes old issuer
		invAcc.removeIssuerByIndex(idx);
		// Sets new shares if needed
		if(totalShares > 0) {
			issuer.setTotalShares(totalShares);
			invAcc.addIssuer(issuer);
		}
		// Updates the balance
		stock.setCurrentBalance(invAcc);
	}
	
	private void buyOrder() {
		// Gets the account to work with
		InvestmentAccount invAcc = stock.getCurrentBalance();
		// Gets the cash form the operation
		Double cash = invAcc.getCash() - order.getTotalSharesPrice();
		// Sets new balance
		invAcc.setCash(cash);
		// Creates a new issuer from the order
		Issuer issuer = new Issuer();
		issuer.setIssuerName(order.getIssuerName());
		issuer.setSharePrice(order.getSharePrice());
		issuer.setTotalShares(order.getTotalShares());
		// Search for the issuer in the account list
		int idx = invAcc.getIssuers().indexOf(issuer);
		// Gets the actual issuer
		Issuer oldIssuer = invAcc.getIssuers().get(idx);
		// substracts from the account the total shares
		Long totalShares = oldIssuer.getTotalShares() + issuer.getTotalShares();
		// Removes old issuer
		invAcc.removeIssuerByIndex(idx);
		// Updates shares
		issuer.setTotalShares(totalShares);
		invAcc.addIssuer(issuer);
		// Updates the balance
		stock.setCurrentBalance(invAcc);
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
		// Follows the order operation
		if(order.getOperation().equals(Operation.BUY)) {
			buyOrder();
		}
		if(order.getOperation().equals(Operation.SELL)) {
			sellOrder();
		}
		return stock;
	}
}
