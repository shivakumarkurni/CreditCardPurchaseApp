package com.credit.service;

import org.springframework.http.ResponseEntity;

import com.credit.dto.CreditCardInputDto;
import com.credit.dto.CreditCardOutputDto;
import com.credit.dto.ResponseDto;
import com.credit.entity.CreditCard;

public interface CreditCardService {
	
public ResponseEntity<CreditCardOutputDto> cardCheck(CreditCardInputDto creditCardInputDto);
public ResponseEntity<ResponseDto> cardCheckOtpVerification(CreditCardInputDto creditCardInputDto);

public CreditCard cardSave(CreditCard creditCard);

	
}
