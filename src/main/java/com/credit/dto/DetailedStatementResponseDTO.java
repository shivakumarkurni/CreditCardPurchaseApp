package com.credit.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DetailedStatementResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer amount;
	private LocalDateTime date;
	private String transactionType;
	private String description;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
