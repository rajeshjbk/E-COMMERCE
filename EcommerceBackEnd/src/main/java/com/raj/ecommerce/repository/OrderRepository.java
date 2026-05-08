package com.raj.ecommerce.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer>{

	//Need to get Order for A Customer with given orderId ==> 2 inputs ==> customerId and OrderId
	@Query("SELECT o from Orders o where o.orderId=:orderId and o.user.userId=:customerId")
	Orders findByOrderIdAndCustomerId(Integer orderId, Integer customerId);
	
	//Need to get Orders based >= orderDate 
	@Query("SELECT o from Orders o where o.orderDate >= :orderDate")
	List<Orders> findByOrderDateGreaterThanEqual(Date orderDate);
	
	//Need to get All Orders for a given UserId
	@Query("SELECT o from Orders o where o.user.userId=:userId")
	List<Orders> getAllOrdersByUserId(Integer userId);
	
	//Need to get All Orders for a given UserName
	@Query("SELECT o from Orders o where o.user.email=:userName")
	List<Orders> getAllOrdersByUserName(String userName);
	
}
