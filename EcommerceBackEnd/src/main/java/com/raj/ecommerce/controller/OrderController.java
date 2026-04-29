package com.raj.ecommerce.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.ecommerce.DTO.OrderDTO;
import com.raj.ecommerce.model.Orders;
import com.raj.ecommerce.service.OrderService;

@RestController
@RequestMapping(value="/ecom/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placed/{userId}")
	public ResponseEntity<?> addOrderToCart(@PathVariable("userId") Integer userId){
		
		OrderDTO placeOrder = orderService.placeOrder(userId);
		
		return ResponseEntity.ok(placeOrder);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Orders> getOrderDetails(@PathVariable("orderId") Integer orderId){
		
		Orders order = orderService.getOrderDetails(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Orders>> getAllUserOrders(@PathVariable("userId") Integer userId){
		
		List<Orders> ordersList = orderService.getAllUserOrders(userId);
		
		return new ResponseEntity<>(ordersList, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Orders>> getAllOrders(){
		
		List<Orders> ordersList = orderService.getAllOrders();
		
		return new ResponseEntity<>(ordersList, HttpStatus.OK);
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<List<Orders>> viewAllOrdersByDate(
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inputDate){
		
		List<Orders> ordersList = orderService.viewAllOrdersByDate(inputDate);
		return new ResponseEntity<>(ordersList, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{userId}/{orderId}")
	public ResponseEntity<String> deleteOrders(@PathVariable Integer userId,
			@PathVariable Integer orderId){
		
		orderService.deleteOrders(userId, orderId);
		
		return new ResponseEntity<>("Order is Deleted Successfully", HttpStatus.OK);
	}
	
}
