package com.credit.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreditCardDto {
	private Integer cardNumber;
	private Integer cvv;
	private LocalDate expireDate;
	private Integer availableLimit ;
	private Integer availableBalance;
	private Integer userId;

}
