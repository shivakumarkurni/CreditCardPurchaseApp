package com.credit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreditCardInputDto {
	
	private String number;
	private Integer cvc;
	private String expiry;
	private String name;
	private Integer amount;
	private Integer userId;

}
