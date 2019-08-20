package com.credit.exception;

public class NoRecordsFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoRecordsFound(String message) {
		
		super(message);
	}

}
