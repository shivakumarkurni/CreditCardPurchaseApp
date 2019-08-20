package com.credit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreditCardOutputDto {
	
	private String message;
	private Integer statusCode;
	private Integer transctionId;

}
