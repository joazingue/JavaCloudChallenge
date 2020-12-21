
package com.gbm.challenge.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gbm.challenge.domains.GBMOrder;
import com.gbm.challenge.domains.InvestmentAccount;
import com.gbm.challenge.domains.StockOperation;
import com.gbm.challenge.services.BusinessValidator;
import com.gbm.challenge.services.InvestmentAccountRespository;

@RestController
public class Endpoints {
	
	@Autowired
	private InvestmentAccountRespository InvAccountRepository;
	@Autowired
	private BusinessValidator validator;
	
	@GetMapping(path = "/sayhello")
	public ResponseEntity<String> sayHello(){
		return new ResponseEntity<String>("Hello from GBM challenge!", HttpStatus.OK);
	}
	
	@PostMapping(path = "/accounts")
	public ResponseEntity<InvestmentAccount> createAccount(@RequestBody InvestmentAccount ia){
		InvestmentAccount savedIA = InvAccountRepository.save(ia);
		return new ResponseEntity<InvestmentAccount>(savedIA, HttpStatus.CREATED);
	}
	
	@PostMapping(path="/accounts/{id}/orders")
	public ResponseEntity<StockOperation> SendOrder(@PathVariable Long id, @RequestBody GBMOrder gbmOrder){
//		System.out.println(id);
//		System.out.println(gbmOrder);
//		System.out.println(validator.getBusinessRules().size());
		// validator.addrule(market)
		// validator.addrule(balance)
		// validator.addrule(stocks)
		// validator.addrule(duplication)
		// validator.validate(gbmOrder)
		// validator[0] ok
		// validator[1] ok
		// validator[2] ok
		// validator[3] ok
		// if one not ok, then return respective invalidation

		
		// Create a new stock operation
		StockOperation stock = new StockOperation();
		// Get the operation account and verify it exists
		Optional<InvestmentAccount> invAccount = InvAccountRepository.findById(id);
		if(invAccount.isEmpty()) {
			return new ResponseEntity<StockOperation>(HttpStatus.NOT_FOUND);
		}
		InvestmentAccount account = invAccount.get();
		// Set stock operation and GBM order for the validator
		stock.setCurrentBalance(account);
		validator.setStockOperation(stock);
		validator.setOrder(gbmOrder);
		// Validates and retreives the stock
		stock = validator.Validate();
		// If stock is valid, saves modified account
		if(stock.IsValid()) {
			InvAccountRepository.save(stock.getCurrentBalance());
		}
		// Return the operation details
		return new ResponseEntity<StockOperation>(stock, HttpStatus.OK);
	}
}
