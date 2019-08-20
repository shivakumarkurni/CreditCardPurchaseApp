package com.credit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreditCardInputDto {
	
	private Long cardNumber;
	private Integer cvv;
	private Integer expiryMoth;
	private Integer expiryYear;
	private String name;
	private Integer amount;
	private Integer userId;



}
