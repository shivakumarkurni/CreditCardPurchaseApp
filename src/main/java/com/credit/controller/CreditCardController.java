package com.credit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.dto.OtpDto;
import com.credit.service.VerifyService;

@RequestMapping("/creditcard")
@RestController
public class CreditCardController {
	
	final static Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);
	
	@Autowired
	private VerifyService verifyService;
	
	@PostMapping("/verification")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpDto otpDto){
		
		LOGGER.info("CreditCardController :: "+otpDto);
		return new ResponseEntity<>(verifyService.verifyOtp(otpDto), HttpStatus.OK);
		
	}

}
