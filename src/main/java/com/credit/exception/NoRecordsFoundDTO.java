package com.credit.exception;

import java.io.Serializable;

public class NoRecordsFoundDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private Integer statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
