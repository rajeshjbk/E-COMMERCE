package com.raj.ecommerce.service;

import com.raj.ecommerce.exception.CartException;
import com.raj.ecommerce.model.Cart;

public interface CartService {

	public Cart addProductToCart(Integer userId, Integer productId) throws CartException;
	
	public Cart increaseProductQuantity(Integer userId, Integer productId) throws CartException;
	
	public Cart decreaseProductQuantity(Integer userId, Integer productId) throws CartException;
	
    public void removeProductFromCart(Integer cartId, Integer productId) throws CartException;
	
	public void removeAllProductsFromCart(Integer cartId) throws CartException;
	
	public Cart getAllCartProducts(Integer cartId) throws CartException;
	
}
