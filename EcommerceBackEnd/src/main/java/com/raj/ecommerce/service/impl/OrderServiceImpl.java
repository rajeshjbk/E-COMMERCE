package com.raj.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.DTO.OrderDTO;
import com.raj.ecommerce.exception.OrderException;
import com.raj.ecommerce.exception.UserException;
import com.raj.ecommerce.model.Cart;
import com.raj.ecommerce.model.CartItem;
import com.raj.ecommerce.model.OrderItem;
import com.raj.ecommerce.model.OrderStatus;
import com.raj.ecommerce.model.Orders;
import com.raj.ecommerce.model.PaymentStatus;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.repository.CartItemRepository;
import com.raj.ecommerce.repository.CartRepository;
import com.raj.ecommerce.repository.OrderItemRepository;
import com.raj.ecommerce.repository.OrderRepository;
import com.raj.ecommerce.repository.ProductRepository;
import com.raj.ecommerce.repository.UserRepository;
import com.raj.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	/*@Override
	public OrderDTO placeOrder(Integer userId) throws OrderException {
	
		User existingUser = userRepository.findById(userId).orElseThrow(()-> new UserException("User Not Found in the Database"));
	
		Cart userCart = existingUser.getCart();
	
		if(userCart.getTotalAmount()==0) {
	
			throw new OrderException("Cart is Empty.. Add the item First");
		}
		Integer cartId = userCart.getCartId();
	
		Orders newOrd = new Orders();
		newOrd.setOrderDate(LocalDateTime.now());
		newOrd.setOrderStatus(OrderStatus.PENDING);
	
		existingUser.getOrders().add(newOrd); //User to Order
		newOrd.setUser(existingUser);  //Order to Order
	
		userRepository.save(existingUser);
		orderRepository.save(newOrd);
	
		//with above two statements user id will be updated in order table
		//order_id will be updated in user table
	
		//now need to save OrderItems
	
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
		for(CartItem itemDto: userCart.getCartItems()) {
	
			if(itemDto.getCart().getCartId()==cartId) {
	
				OrderItem ordItem = new OrderItem();
				ordItem.setProduct(itemDto.getProduct());
				ordItem.setOrderId(ordItem.getOrderId());
				ordItem.setQuantity(itemDto.getQuantity());
				orderItems.add(ordItem);
			}
			newOrd.setOrderItem(orderItems); //Order to OrderItems
			newOrd.setTotalAmount(userCart.getTotalAmount());
			orderRepository.save(newOrd);
		}
	
		//Preparing Return Type
		OrderDTO outputObj = new OrderDTO();
		outputObj.setOrderId(newOrd.getOrderId());
		outputObj.setOrderDate(newOrd.getOrderDate());
		outputObj.setOrderAmount(newOrd.getTotalAmount());
		outputObj.setStatus(OrderStatus.PENDING.toString());
		outputObj.setPaymentStatus(PaymentStatus.PENDING.toString());
	
		return outputObj;
	}
	*/
	
	@Override
	public OrderDTO placeOrder(Integer userId) throws OrderException {

	    User existingUser = userRepository.findById(userId)
	            .orElseThrow(() -> new UserException("User Not Found in Database"));

	    Cart userCart = existingUser.getCart();

	    if (userCart == null || userCart.getCartItems().isEmpty()) {
	        throw new OrderException("Cart is Empty.. Add item first");
	    }

	    Orders newOrd = new Orders();
	    newOrd.setOrderDate(LocalDateTime.now());
	    newOrd.setOrderStatus(OrderStatus.PENDING);
	    newOrd.setTotalAmount(userCart.getTotalAmount());

	    // User <-> Order Mapping
	    newOrd.setUser(existingUser);

	    if (existingUser.getOrders() == null) {
	        existingUser.setOrders(new ArrayList<>());
	    }

	    existingUser.getOrders().add(newOrd);

	    // Save Order First
	    Orders savedOrder = orderRepository.save(newOrd);

	    // Create Order Items
	    List<OrderItem> orderItems = new ArrayList<>();

	    for (CartItem cartItem : userCart.getCartItems()) {

	        OrderItem ordItem = new OrderItem();
	        ordItem.setProduct(cartItem.getProduct());
	        ordItem.setQuantity(cartItem.getQuantity());

	        // Link OrderItem with Order
	        ordItem.setOrder(savedOrder);

	        orderItems.add(ordItem);
	    }

	    // Save all order items
	    orderItems = orderItemRepository.saveAll(orderItems);

	    // Set Order Items to Order
	    savedOrder.setOrderItem(orderItems);

	    orderRepository.save(savedOrder);

	    // Clear Cart After Order
	    userCart.getCartItems().clear();
	    userCart.setTotalAmount(0.0);
	    cartRepository.save(userCart);

	    // Return DTO
	    OrderDTO outputObj = new OrderDTO();
	    outputObj.setOrderId(savedOrder.getOrderId());
	    outputObj.setOrderDate(savedOrder.getOrderDate());
	    outputObj.setOrderAmount(savedOrder.getTotalAmount());
	    outputObj.setStatus(OrderStatus.PENDING.toString());
	    outputObj.setPaymentStatus(PaymentStatus.PENDING.toString());

	    return outputObj;
	}
	
	@Override
	public Orders getOrderDetails(Integer orderId) throws OrderException {

		Orders existingOrder = orderRepository.findById(orderId).orElseThrow(()-> new OrderException("Order Not Found in the Database"));

		return existingOrder;
	}

	@Override
	public List<Orders> getAllUserOrders(Integer userId) throws OrderException {

		try {

			List<Orders> ordersList = orderRepository.getAllOrdersByUserId(userId);

			if(ordersList.isEmpty()) {
				throw new OrderException("No Orders Found for the User");
			}
			return ordersList;

		}catch (Exception ex) {

			throw new OrderException("Exception in Fetching Order Details by User"+ex.getMessage());
		}	
	}

	@Override
	public List<Orders> getAllOrders() throws OrderException {

		try {

			List<Orders> ordersList = orderRepository.findAll();

			if(ordersList.isEmpty()) {
				throw new OrderException("No Orders Found in the System");
			}
			return ordersList;

		}catch (Exception ex) {

			throw new OrderException("Exception in Fetching Order Details "+ex.getMessage());
		}	
	}

	@Override
	public List<Orders> viewAllOrdersByDate(Date orderDate) throws OrderException {

		try {

			List<Orders> ordersList = orderRepository.findByOrderDateGreaterThanEqual(orderDate);

			if(ordersList.isEmpty()) {
				throw new OrderException("No Orders Found with the Specified Date");
			}
			return ordersList;

		}catch (Exception ex) {

			throw new OrderException("Exception in Fetching Order with the Specified Date "+ex.getMessage());
		}	
	}

	@Override
	public void deleteOrders(Integer userId, Integer orderId) throws OrderException {

		User existingUser = userRepository.findById(userId).orElseThrow(()-> new UserException("No User Found in the Database"));
		
        Orders existingOrder = orderRepository.findById(orderId).orElseThrow(()-> new OrderException("No Orders Found in the Database"));
        
        orderRepository.delete(existingOrder);
	}
}






