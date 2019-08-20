package com.credit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.dto.CreditCardInputDto;
import com.credit.dto.CreditCardOtpVerificationInput;
import com.credit.dto.CreditCardOutputDto;
import com.credit.dto.OtpDto;
import com.credit.dto.ResponseDto;
import com.credit.entity.CreditCard;
import com.credit.service.CreditCardService;
import com.credit.service.VerifyService;
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/creditcard")
@RestController
public class CreditCardController {

	final static Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

	@Autowired
	private VerifyService verifyService;

	@Autowired
	CreditCardService creditCardService;

	@PostMapping("/")
	public ResponseEntity<CreditCardOutputDto> cardCheck(@RequestBody CreditCardInputDto creditCardInputDto) {
		LOGGER.info("CreditCardController --> cardCheck");
		return creditCardService.cardCheck(creditCardInputDto);
	}

	@PostMapping("/verification/")
	public ResponseEntity<ResponseDto> cardCheckOtpVerification(
			@RequestBody CreditCardOtpVerificationInput creditCardOtpVerificationInput) {

		LOGGER.info("CreditCardController --> cardCheckOtpVerification");
		return creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		}

	@PostMapping("/cardSave")
	public CreditCard cardSave(@RequestBody CreditCard creditCard) {

		LOGGER.info("CreditCardController --> cardSave");
		return creditCardService.cardSave(creditCard);
	}

	@PostMapping("/verification")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpDto otpDto) {

		LOGGER.info("CreditCardController otp: {}" , otpDto);
		return new ResponseEntity<>(verifyService.verifyOtp(otpDto), HttpStatus.OK);

	}
}
