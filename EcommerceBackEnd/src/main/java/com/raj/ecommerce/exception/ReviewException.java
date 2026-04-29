package com.raj.ecommerce.exception;

public class ReviewException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ReviewException() {

	}

	public ReviewException(String msg) {

		super(msg);
	}
}
