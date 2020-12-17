
package com.gbm.challenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gbm.challenge.domains.InvestmentAccount;

@RestController
public class endpoints {
	
	@GetMapping(path = "/sayhello")
	public ResponseEntity<String> sayHello(){
		return new ResponseEntity<String>("Hello from GBM challenge!", HttpStatus.OK);
	}
	
	@PostMapping(path = "/accounts")
	public ResponseEntity<InvestmentAccount> createAccount(@RequestBody String cash){
		InvestmentAccount ia = new InvestmentAccount();
		ia.setCash(Double.parseDouble(cash));
		return new ResponseEntity<InvestmentAccount>(ia, HttpStatus.CREATED);
	}
}
