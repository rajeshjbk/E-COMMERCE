package com.raj.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem c WHERE c.cart.cartId = :cartId AND c.product.productId = :productId")
	void removeProductFromCart(@Param("cartId") Integer cartId, @Param("productId") Integer productId);

	/*@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.product.productId =: productId AND ci.cart.cartId =: cartId")
	public void removeProductFromCart(Integer cartId, Integer productId);*/


	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.cart.cartId =: cartId")
	public void removeAllProductsFromCart(@Param("cartId") Integer cartId);

	// Additional added methods

	CartItem findByCartCartIdAndProductProductId(Integer cartId, Integer productId);

	List<CartItem> findByCartCartId(Integer cartId);

}
