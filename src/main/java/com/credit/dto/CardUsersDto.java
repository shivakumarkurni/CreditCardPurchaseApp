package com.credit.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CardUsersDto {
	private String name;
	private String email;
	private LocalDate dob;
	private Integer phone;
	private String address;
	private String password;

}
