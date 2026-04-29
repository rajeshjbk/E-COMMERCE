package com.raj.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.product.productId =: productId AND ci.cart.cartId =: cartId")
	public void removeProductFromCart(Integer cartId, Integer productId);
	
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.cart.cartId =: cartId")
	public void removeAllProductsFromCart(Integer cartId);
	
}
