package com.oldBookSell.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oldBookSell.model.Payment;

@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
