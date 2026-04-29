package com.raj.ecommerce.service;

import java.util.Date;
import java.util.List;

import com.raj.ecommerce.DTO.OrderDTO;
import com.raj.ecommerce.exception.OrderException;
import com.raj.ecommerce.model.Orders;

public interface OrderService {

	public OrderDTO placeOrder(Integer userId) throws OrderException;  //Creating New Order
		
	public Orders getOrderDetails(Integer orderId) throws OrderException;
	
	public List<Orders> getAllUserOrders(Integer userId) throws OrderException;
	
	public List<Orders> getAllOrders() throws OrderException;
	
	public List<Orders> viewAllOrdersByDate(Date orderDate) throws OrderException;
	
	public void deleteOrders(Integer userId, Integer orderId) throws OrderException;
	
}
