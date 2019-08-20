package com.credit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cardId;
	private Integer cardNumber;
	private Integer cvv;
	private LocalDate expireDate;
	private Integer availableLimit ;
	private Integer availableBalance;
	private Integer userId;
	
	

}
