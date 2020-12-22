package com.gbm.challenge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;
import com.gbm.challenge.domains.Issuer;
import com.gbm.challenge.domains.StockOperation;
import com.gbm.challenge.services.rules.IBusinessRules;
import com.gbm.challenge.services.rules.Operation;
import com.gbm.challenge.services.rules.Validation;

// Can implement Abstract factory or Rule addition
/**
 * Business validator modifies the accounts after each of business rules are validated.
 */
@Service
public class BusinessValidator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IssuerRepository issuerRepository;
	
	private StockOperation stock;
	private GBMOrder order;
	private Validation validation = Validation.INVALID_OPERATION;
	private List<IBusinessRules> businessRules = new ArrayList<IBusinessRules>();
	
	/**
	 * Set the actual stock with the current balance
	 * @param stock contains the current balance operating.
	 * @return Nothing.
	 */
	public void setStockOperation(StockOperation stock) {
		this.stock = stock;
	}
	/**
	 * Set order sent
	 * @param order will be validated according to the business rules
	 * @return Nothing.
	 */
	public void setOrder(GBMOrder order) {
		this.order = order;
	}
	/**
	 * Add business rules to the validator
	 * @param busRule add new rule.
	 * @return Nothing.
	 */
	public void AddBusinessRule(IBusinessRules busRule) {
		businessRules.add(busRule);
	}
	/**
	 * Gets all business rules
	 * @param stock contains the current balance operating.
	 * @return Nothing.
	 */
	public List<IBusinessRules> getBusinessRules() {
		return businessRules;
	}
	
	/**
	 * Update the account according to the SELL operation
	 * @exception Exception if current balance is incorrect.
	 * @see ValidateStocks
	 */
	private InvestmentAccount sellOrder() throws Exception {
		// Gets the account to work with
		InvestmentAccount invAcc = stock.getCurrentBalance();
		// Gets the cash form the operation
		Double cash = invAcc.getCash() + order.getTotalSharesPrice();
		// Sets new balance
		invAcc.setCash(cash);
		// Creates a new issuer from the order
		Issuer issuer = new Issuer();
		issuer.setIssuerName(order.getIssuer_name());
		issuer.setSharePrice(order.getShare_price());
		issuer.setTotalShares(order.getTotal_shares());
		
		// Search for the issuer in the account list
		// Search for the issuer in the account list
		Optional<Issuer> exisIssuer = invAcc.getIssuers().stream().filter(ord -> (ord.getIssuerName().equals(issuer.getIssuerName()))).findFirst();
		if (exisIssuer.isPresent()) {
			// Gets the actual issuer
			Issuer oldIssuer = exisIssuer.get();
			// substracts from the account the total shares
			Long totalShares = oldIssuer.getTotalShares() - issuer.getTotalShares();
			// Removes old issuer
			invAcc.removeIssuer(oldIssuer);
			if(totalShares > 0) {
				issuerRepository.save(issuer);
				issuer.setTotalShares(totalShares);
				invAcc.addIssuer(issuer);
			} else {
				issuerRepository.delete(issuer);
			}
		} else {
			throw new Exception("Order can't sell");
		}
		return invAcc;
	}
	
	/**
	 * Update the account according to the BUY operation
	 */
	private InvestmentAccount buyOrder() {
		// Gets the account to work with
		InvestmentAccount invAcc = stock.getCurrentBalance();
		// Gets the cash form the operation
		Double cash = invAcc.getCash() - order.getTotalSharesPrice();
		// Sets new balance
		invAcc.setCash(cash);
		// Creates a new issuer from the order
		Issuer issuer = new Issuer();
		issuer.setIssuerName(order.getIssuer_name());
		issuer.setSharePrice(order.getShare_price());
		issuer.setTotalShares(order.getTotal_shares());
		// Search for the issuer in the account list
		Optional<Issuer> exisIssuer = invAcc.getIssuers().stream().filter(ord -> (ord.getIssuerName().equals(issuer.getIssuerName()))).findFirst();
		if (exisIssuer.isPresent()) {
			// Gets the actual issuer
			Issuer oldIssuer = exisIssuer.get();
			// substracts from the account the total shares
			Long totalShares = oldIssuer.getTotalShares() + issuer.getTotalShares();
			// Removes old issuer
			invAcc.removeIssuer(oldIssuer);
			// Updates shares
			issuer.setTotalShares(totalShares);
		}
		issuerRepository.save(issuer);
		invAcc.addIssuer(issuer);
		return invAcc;
	}
	
	/**
	 * Validates the order according to the current balance.
	 * @param set stock and order before executing.
	 * @return Stock updated.
	 * @exception Exception On missing params.
	 * @see setOrder, setStockOperation
	 */
	public StockOperation Validate() {
		try {
			if(stock == null || order == null) throw new Exception("Missing params on validation");
			// Validate each business rule
			for (IBusinessRules iBusinessRules : businessRules) {
				// Set the GBM order for the rule
				iBusinessRules.SetOrder(order);
				// Sets Account
				iBusinessRules.SetAccount(stock.getCurrentBalance());
				// Get the validation
				validation = iBusinessRules.validate();
				// If validation is not CORRECT, returns bussiness error
				if(!validation.equals(Validation.CORRECT)) {
					stock.addBusinessError(validation);
					return stock;
				}
			}
			// Follows the order operation
			if(order.getOperation().equals(Operation.BUY)) {
				stock.setCurrentBalance(buyOrder());
			}
			if(order.getOperation().equals(Operation.SELL)) {
				stock.setCurrentBalance(sellOrder());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.getLocalizedMessage());
		}
		return stock;
	}
}
