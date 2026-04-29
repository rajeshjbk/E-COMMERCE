package com.raj.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.ecommerce.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
