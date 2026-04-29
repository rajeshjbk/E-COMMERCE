package com.raj.ecommerce.exception;

public class AddressException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AddressException() {

	}

	public AddressException(String msg) {

		super(msg);
	}
}
