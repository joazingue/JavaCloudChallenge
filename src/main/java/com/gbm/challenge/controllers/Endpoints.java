
package com.gbm.challenge.controllers;

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
import com.gbm.challenge.domains.Stock;
import com.gbm.challenge.services.InvestmentAccountRespository;

@RestController
public class Endpoints {
	
	@Autowired
	private InvestmentAccountRespository InvAccountRepository;
	
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
	public ResponseEntity<Stock> SendOrder(@PathVariable Long id, @RequestBody GBMOrder gbmOrder){
		System.out.println(id);
		System.out.println(gbmOrder);
		Stock s = new Stock();
		return new ResponseEntity<Stock>(s, HttpStatus.OK);
	}
}
