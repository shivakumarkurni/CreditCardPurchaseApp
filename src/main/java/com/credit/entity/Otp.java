package com.credit.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Otp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer otpId;
	private Integer otpValue;
	private LocalDateTime date;
	
	private Integer transactionId;

}