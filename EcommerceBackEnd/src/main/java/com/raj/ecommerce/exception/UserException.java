package com.raj.ecommerce.exception;

public class UserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserException() {

		super();
	}

	public UserException(String msg) {

		super(msg);
	}
}
