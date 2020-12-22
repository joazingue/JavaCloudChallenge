
package com.gbm.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbm.challenge.domains.InvestmentAccount;
import com.gbm.challenge.domains.StockOperation;
import com.gbm.challenge.services.rules.Validation;

import net.minidev.json.JSONObject;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class GbmChallengeApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private static String POSTcreateAccount = "/accounts";
	private static String POSTsendOrder = "/accounts/{idInvestmentAccount}/orders";
	
	/*
	 * Test challenge cases
	 */
	@Test
	void testCaseChallenge() throws Exception {
		// Create a investment account
		JSONObject content = new JSONObject();
		content.appendField("cash", 1000);
		// Make request
		MvcResult mockRequest = mockMvc
				.perform(post(POSTcreateAccount)
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		assert(mockRequest.getResponse().getStatus() == HttpStatus.CREATED.value());
		InvestmentAccount ia = new ObjectMapper()
				.readValue(mockRequest.getResponse().getContentAsString(), 
						InvestmentAccount.class);
		assert(ia != null);
		
		// Send BUY order
		content = new JSONObject();
		content.appendField("timestamp", 1571325625);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 2);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		StockOperation stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getCurrentBalance().getCash() == 900);
		assert(stock.getCurrentBalance().getIssuers().size() == 1);
		
		// Send second order
		content = new JSONObject();
		content.appendField("timestamp", 1583362645);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "NFTX");
		content.appendField("total_shares", 10);
		content.appendField("share_price", 80);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().get(0) == Validation.CLOSED_MARKET);
	}
	
	@Test
	void TestBalanceRule() throws Exception {
		// Create a investment account
		JSONObject content = new JSONObject();
		content.appendField("cash", 1000);

		MvcResult mockRequest = mockMvc
				.perform(post(POSTcreateAccount)
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		assert(mockRequest.getResponse().getStatus() == HttpStatus.CREATED.value());
		InvestmentAccount ia = new ObjectMapper()
				.readValue(mockRequest.getResponse().getContentAsString(), 
						InvestmentAccount.class);
		assert(ia != null);

		// Send BUY order
		content = new JSONObject();
		content.appendField("timestamp", 1571325625);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 21);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();

		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		StockOperation stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().get(0) == Validation.INSUFFICIENT_BALANCE);
	}
	
	@Test
	void TestDuplicationRule() throws Exception {
		// Create a investment account
		JSONObject content = new JSONObject();
		content.appendField("cash", 1000);

		MvcResult mockRequest = mockMvc
				.perform(post(POSTcreateAccount)
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		assert(mockRequest.getResponse().getStatus() == HttpStatus.CREATED.value());
		InvestmentAccount ia = new ObjectMapper()
				.readValue(mockRequest.getResponse().getContentAsString(), 
						InvestmentAccount.class);
		assert(ia != null);

		// Send BUY order
		content = new JSONObject();
		content.appendField("timestamp", 1571325625);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 2);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();

		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		StockOperation stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		
		// Send second order
		content = new JSONObject();
		content.appendField("timestamp", 1571325625 + 300000);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 2);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().get(0) == Validation.DUPLICATED_OPERATION);
	}
	
	@Test
	void TestMarketRule() throws Exception {
		// Create a investment account
		JSONObject content = new JSONObject();
		content.appendField("cash", 1000);
		
		MvcResult mockRequest = mockMvc
				.perform(post(POSTcreateAccount)
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		assert(mockRequest.getResponse().getStatus() == HttpStatus.CREATED.value());
		InvestmentAccount ia = new ObjectMapper()
				.readValue(mockRequest.getResponse().getContentAsString(), 
						InvestmentAccount.class);
		assert(ia != null);
		
		// Send BUY order
		content = new JSONObject();
		content.appendField("timestamp", 1583362645);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "NFTX");
		content.appendField("total_shares", 10);
		content.appendField("share_price", 80);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		StockOperation stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().get(0) == Validation.CLOSED_MARKET);
	}
	
	@Test
	void TestStockRule() throws Exception {
		// Create a investment account
		JSONObject content = new JSONObject();
		content.appendField("cash", 1000);
		
		MvcResult mockRequest = mockMvc
				.perform(post(POSTcreateAccount)
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		assert(mockRequest.getResponse().getStatus() == HttpStatus.CREATED.value());
		InvestmentAccount ia = new ObjectMapper()
				.readValue(mockRequest.getResponse().getContentAsString(), 
						InvestmentAccount.class);
		assert(ia != null);
		
		// Send BUY order
		content = new JSONObject();
		content.appendField("timestamp", 1571325625);
		content.appendField("operation", "BUY");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 2);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		StockOperation stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().size() == 0);
		
		// Send second order
		content = new JSONObject();
		content.appendField("timestamp", 1571310616);
		content.appendField("operation", "SELL");
		content.appendField("issuer_name", "AAPL");
		content.appendField("total_shares", 3);
		content.appendField("share_price", 50);
		mockRequest = mockMvc
				.perform(post(POSTsendOrder, ia.getIdInvestmentAccount())
						.contentType("application/json")
						.content(content.toJSONString()))
				.andReturn();
		
		assert(mockRequest.getResponse().getStatus() == HttpStatus.OK.value());
		stock = new ObjectMapper().readValue(mockRequest.getResponse().getContentAsString(), StockOperation.class);
		assert(stock.getBusinessErrors().get(0) == Validation.INSUFFICIENT_STOCKS);
	}
}
