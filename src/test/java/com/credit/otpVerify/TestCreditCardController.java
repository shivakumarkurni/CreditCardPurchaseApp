package com.credit.otpVerify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.credit.controller.CreditCardController;
import com.credit.dto.CreditCardInputDto;
import com.credit.dto.CreditCardOtpVerificationInput;
import com.credit.dto.OtpDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class TestCreditCardController {

	private MockMvc mockMvc;

	@InjectMocks
	private CreditCardController creditCardController;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();
	}
	
	@Test
	public void testVerifyOtpController() throws Exception {
		OtpDto otpDto = new OtpDto(1010, 1234);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonObject = objectMapper.writeValueAsString(otpDto);
		mockMvc.perform(post("/creditcard/verification").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andExpect(status().isOk());
	}
	
	@Test
	public void testCardCheck() throws Exception {
		CreditCardInputDto creditCardInputDto = new CreditCardInputDto();
		creditCardInputDto.setAmount(4000);
		creditCardInputDto.setNumber("1234567891234");
		creditCardInputDto.setCvc(1234);
		creditCardInputDto.setExpiry("11/22");
//		creditCardInputDto.setExpiry(21);

		creditCardInputDto.setCvc(234);
		creditCardInputDto.setExpiry("2021-04-12");
		creditCardInputDto.setName("LAXMAN VERMA");
		creditCardInputDto.setNumber("123456789123");
		creditCardInputDto.setUserId(101);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonObject = objectMapper.writeValueAsString(creditCardInputDto);
		mockMvc.perform(post("/creditcard").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andExpect(status().isOk());
	}
	
	@Test
	public void testCardCheckOtpVerification() throws Exception {
		CreditCardOtpVerificationInput creditCardOtpVerificationInput = new CreditCardOtpVerificationInput();
		creditCardOtpVerificationInput.setOtpValue(1234);
		creditCardOtpVerificationInput.setTransctionId(1010);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonObject = objectMapper.writeValueAsString(creditCardOtpVerificationInput);
		mockMvc.perform(post("/creditcard/verification").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andExpect(status().isOk());
	}
	
/*	@Test
	public void testCardSave() throws Exception {
		CreditCard creditCard = new CreditCard();
		creditCard.setAvailableBalance(6000);
		creditCard.setAvailableLimit(10000);
		creditCard.setCardId(101);
		creditCard.setCardNumber(123456788912L);
		creditCard.setCvv(123);
		creditCard.setExpireDate(LocalDate.of(2019, 12, 23));
		creditCard.setUserId(131);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonObject = objectMapper.writeValueAsString(creditCard);
		mockMvc.perform(post("/creditcard/cardSave").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andExpect(status().isOk());
		
	}*/
}