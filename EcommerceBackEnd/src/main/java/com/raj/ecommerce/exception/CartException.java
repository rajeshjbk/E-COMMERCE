package com.raj.ecommerce.exception;

public class CartException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CartException() {

	}

	public CartException(String msg) {

		super(msg);
	}
}
