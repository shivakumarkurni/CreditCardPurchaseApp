package com.credit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreditCardOtpVerificationInput {
	
	private Integer TransactionId;
	private Integer otp;


}
