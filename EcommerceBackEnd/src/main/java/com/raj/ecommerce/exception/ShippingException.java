package com.raj.ecommerce.exception;

public class ShippingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ShippingException() {

	}

	public ShippingException(String msg) {

		super(msg);
	}
}
