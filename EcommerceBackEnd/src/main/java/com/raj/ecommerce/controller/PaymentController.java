package com.raj.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.model.Payment;
import com.raj.ecommerce.service.PaymentService;

@RestController
@RequestMapping(value = "/ecom/order-payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(value = "/makePayment")
	public ResponseEntity<Payment> makePayment(@RequestParam Integer orderId, @RequestParam Integer userId){
		
		Payment payment = paymentService.makePayment(orderId, userId);
		return ResponseEntity.ok(payment);
	}
}
