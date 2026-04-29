package com.raj.ecommerce.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.exception.OrderException;
import com.raj.ecommerce.exception.PaymentException;
import com.raj.ecommerce.exception.UserException;
import com.raj.ecommerce.model.OrderStatus;
import com.raj.ecommerce.model.Orders;
import com.raj.ecommerce.model.Payment;
import com.raj.ecommerce.model.PaymentMethod;
import com.raj.ecommerce.model.PaymentStatus;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.repository.OrderRepository;
import com.raj.ecommerce.repository.PaymentRepository;
import com.raj.ecommerce.repository.UserRepository;
import com.raj.ecommerce.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Payment makePayment(Integer orderId, Integer userId) throws PaymentException {
		
		User existUser = userRepository.findById(userId).orElseThrow(
				()-> new UserException("User is Not Available"));
		
		Orders ordObj = orderRepository.findById(orderId).orElseThrow(
				()-> new OrderException("Order is Not Available"));
		
		if(ordObj==null) {
			
			throw new OrderException("Order not found for the given customer");
		}
		
		Payment payment = new Payment();
		payment.setPaymentAmount(ordObj.getTotalAmount());
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentMethod(PaymentMethod.UPI);
		payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
		payment.setUser(existUser);
		payment.setOrder(ordObj);
		
		paymentRepository.save(payment);
		
		ordObj.setOrderStatus(OrderStatus.SHIPPED);
		ordObj.setPayment(payment);
		
		orderRepository.save(ordObj);
		
		existUser.getPayments().add(payment);
		userRepository.save(existUser);
		
		return payment;
	}

}
