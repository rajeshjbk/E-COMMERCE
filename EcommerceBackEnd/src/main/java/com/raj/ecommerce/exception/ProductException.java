package com.raj.ecommerce.exception;

public class ProductException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProductException() {

	}

	public ProductException(String msg) {

		super(msg);
	}
}
