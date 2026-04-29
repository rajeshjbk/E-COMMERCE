package com.raj.ecommerce.service;

import com.raj.ecommerce.exception.PaymentException;
import com.raj.ecommerce.model.Payment;

public interface PaymentService {

	Payment makePayment(Integer orderId, Integer userId) throws PaymentException;
}
