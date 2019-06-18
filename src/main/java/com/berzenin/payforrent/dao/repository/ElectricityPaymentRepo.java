package com.berzenin.payforrent.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.payforrent.model.payment.ElectricityPayment;

@Repository
public interface ElectricityPaymentRepo extends JpaRepository<ElectricityPayment, Long> {

	ElectricityPayment findByName(String string);
	
}
