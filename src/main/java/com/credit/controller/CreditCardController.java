package com.credit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.dto.CreditCardInputDto;
import com.credit.dto.CreditCardOtpVerificationInput;
import com.credit.dto.CreditCardOutputDto;
import com.credit.dto.ResponseDto;
import com.credit.entity.CreditCard;
import com.credit.service.CreditCardService;

@RequestMapping("/creditcard")
@RestController
public class CreditCardController {

	@Autowired
	CreditCardService creditCardService;

	@PostMapping("/")
	public ResponseEntity<CreditCardOutputDto> cardCheck(@RequestBody CreditCardInputDto creditCardInputDto) {
		return creditCardService.cardCheck(creditCardInputDto);

	}
	
	
	@PostMapping("/verification/")
	public ResponseEntity<ResponseDto> cardCheckOtpVerification(@RequestBody CreditCardOtpVerificationInput creditCardOtpVerificationInput) {
		
		
		return creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		
	}
	
	
	@PostMapping("/cardSave")
	public CreditCard cardSave(@RequestBody CreditCard creditCard) {
		
		
		return creditCardService.cardSave(creditCard);

	}

}
