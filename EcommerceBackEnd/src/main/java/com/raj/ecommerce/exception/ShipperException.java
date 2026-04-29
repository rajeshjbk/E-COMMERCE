package com.raj.ecommerce.exception;

public class ShipperException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ShipperException() {

	}

	public ShipperException(String msg) {

		super(msg);
	}
}
