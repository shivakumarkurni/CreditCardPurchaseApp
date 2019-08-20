package com.credit.otpVerify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.credit.controller.CreditCardController;
import com.credit.dto.OtpDto;
import com.credit.service.VerifyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class testVerifyOtpController {

	@Mock
	private VerifyServiceImpl verifyServiceImpl;

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
}