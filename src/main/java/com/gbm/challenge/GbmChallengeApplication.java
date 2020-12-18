package com.gbm.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gbm.challenge.services.BusinessValidator;
import com.gbm.challenge.services.rules.IBusinessRules;
import com.gbm.challenge.services.rules.ValidateBalance;

@SpringBootApplication
public class GbmChallengeApplication implements CommandLineRunner {

	@Autowired
	private BusinessValidator validator;
	
	public static void main(String[] args) {
		SpringApplication.run(GbmChallengeApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		ValidateBalance balanceRule = new ValidateBalance();
		validator.AddBusinessRule(balanceRule);
	}

}
