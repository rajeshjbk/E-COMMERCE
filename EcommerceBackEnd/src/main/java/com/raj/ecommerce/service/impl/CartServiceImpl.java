package com.raj.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.ecommerce.exception.CartException;
import com.raj.ecommerce.exception.ProductException;
import com.raj.ecommerce.exception.UserException;
import com.raj.ecommerce.model.Cart;
import com.raj.ecommerce.model.CartItem;
import com.raj.ecommerce.model.Product;
import com.raj.ecommerce.model.User;
import com.raj.ecommerce.repository.CartItemRepository;
import com.raj.ecommerce.repository.CartRepository;
import com.raj.ecommerce.repository.ProductRepository;
import com.raj.ecommerce.repository.UserRepository;
import com.raj.ecommerce.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;


	@Override
	public Cart addProductToCart(Integer userId, Integer productId) throws CartException {

		Product existProd = productRepository.findById(productId).orElseThrow(()-> new ProductException("Product is Not Available in Stock"));

		User existUser = userRepository.findById(userId).orElseThrow(()-> new UserException("User is Not Available"));

		//Below code will verify Product is Already available in Cart. If available we will throw exception.
		if(existUser.getCart()!=null) {

			Cart userCart = existUser.getCart();

			List<CartItem> cartItems = userCart.getCartItems();

			if(cartItems != null) {

				for(int i=0; i < cartItems.size(); i++) {

					if(cartItems.get(i).getProduct().getProductId()==productId && cartItems.get(i).getCart().getCartId() == userCart.getCartId()) {

						throw new CartException("Already Product Exists in Cart You can Either Decrease or Increase");
					}
				}
			}

			CartItem cartItem = new CartItem();
			cartItem.setProduct(existProd);
			cartItem.setQuantity(1);
			cartItem.setCart(userCart);
			userCart.getCartItems().add(cartItem);

			userCart.setTotalAmount(calculateCartTotal(cartItems));

			return cartRepository.save(userCart);

		}else {

			//First time User is adding Product to the Cart
			Cart newCart = new Cart();
			newCart.setUser(existUser);
			existUser.setCart(newCart);

			CartItem cartItem = new CartItem();
			cartItem.setProduct(existProd);
			cartItem.setQuantity(1);

			newCart.getCartItems().add(cartItem);
			cartItem.setCart(newCart);

			newCart.setTotalAmount(calculateCartTotal(newCart.getCartItems()));
			userRepository.save(existUser);

			return existUser.getCart();
		}
	}

	public double calculateCartTotal(List<CartItem> cartItems) {

		double totalAmt = 0.0;
		for(CartItem item: cartItems) {
			double itemPrice = item.getProduct().getPrice();
			int itemQty = item.getQuantity();
			totalAmt += (itemPrice * itemQty);
		}

		return totalAmt;
	}

	@Override
	public Cart increaseProductQuantity(Integer userId, Integer productId) throws CartException {

		User existUser = userRepository.findById(userId).orElseThrow(()-> new UserException("User is Not Available"));

		if(existUser.getCart() == null) {

			throw new UserException("User Cart is Not available");
		}

		Cart userCart = existUser.getCart();
		List<CartItem> cartItems = userCart.getCartItems();

		//Find the Product from Cart which should match with productId and increase
		//the quantity to the matched product
		CartItem cartItemToUpdate = cartItems.stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)
						&& item.getCart().getCartId().equals(userCart.getCartId())
						).findFirst()
				.orElseThrow(()-> new CartException("Cart Item Not Found"));

		//After match found, then we can increase the quantity
		int qty = cartItemToUpdate.getQuantity();
		cartItemToUpdate.setQuantity(qty+1);
		cartItemRepository.save(cartItemToUpdate);

		userCart.setCartItems(cartItems);
		userCart.setTotalAmount(calculateCartTotal(cartItems));
		cartRepository.save(userCart);

		return userCart;
	}

	@Override
	public Cart decreaseProductQuantity(Integer userId, Integer productId) throws CartException {
		User existUser = userRepository.findById(userId).orElseThrow(()-> new UserException("User is Not Available"));

		if(existUser.getCart() == null) {

			throw new UserException("User Cart is Not available");
		}

		Cart userCart = existUser.getCart();
		List<CartItem> cartItems = userCart.getCartItems();

		//Find the Product from Cart which should match with productId and decrease
		//the quantity to the matched product
		CartItem cartItemToUpdate = cartItems.stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)
						&& item.getCart().getCartId().equals(userCart.getCartId())
						).findFirst()
				.orElseThrow(()-> new CartException("Cart Item Not Found"));

		//After match found, then we can decrease the quantity
		int qty = cartItemToUpdate.getQuantity();
		if(qty==1) {
			throw new CartException("Product Can't be further Decrease....");
		}

		if(qty>1) {

			cartItemToUpdate.setQuantity(qty-1);
			cartItemRepository.save(cartItemToUpdate);

			userCart.setCartItems(cartItems);
			userCart.setTotalAmount(calculateCartTotal(cartItems));
			cartRepository.save(userCart);

		}else {

			cartItems.remove(cartItemToUpdate);
			userCart.setCartItems(cartItems);
			userCart.setTotalAmount(calculateCartTotal(cartItems));
			cartRepository.save(userCart);
		}
		return userCart;
	}

	@Override
	public void removeProductFromCar(Integer cartId, Integer productId) throws CartException {

		Cart existCart = cartRepository.findById(cartId).
				orElseThrow(()-> new CartException("Cart Not Found"));

		cartItemRepository.removeProductFromCart(cartId, productId);

		List<CartItem> list = existCart.getCartItems();
		existCart.setTotalAmount(calculateCartTotal(list));
		cartRepository.save(existCart);

	}

	@Override
	public void removeAllProductsFromCart(Integer cartId) throws CartException {
	
		Cart existCart = cartRepository.findById(cartId).
				orElseThrow(()-> new CartException("Cart Not Found"));
		
		cartItemRepository.removeAllProductsFromCart(cartId);
		existCart.setTotalAmount(0.0);
		cartRepository.save(existCart);
	}

	@Override
	public Cart getAllCartProducts(Integer cartId) throws CartException {
		
		Cart existCart = cartRepository.findById(cartId).
				orElseThrow(()-> new CartException("Cart Not Found"));
		
		List<CartItem> cartItems = existCart.getCartItems();
		List<Product> products = new ArrayList<>();
		
		for(CartItem cartItem : cartItems) {
			
			if(cartItem.getCart().getCartId() == cartId) {
				
				Product prod = cartItem.getProduct();
				products.add(prod);
			}
		}
		
		if(products.isEmpty()) {
			
			throw new ProductException("Cart Is Empty");
		}
		
		return existCart;
	}
}
