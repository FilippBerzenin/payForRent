package com.berzenin.payforrent.dao.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.payforrent.model.payment.ElectricityPayment;

@RunWith(SpringRunner.class)
@DataJpaTest
class ElectricityPaymentRepoTest {
	
//	@Autowired
//	private ElectricityPayment electricityPayment;
	
	@Autowired
	private ElectricityPaymentRepo repo;

	@Test
	void test() {
		//given
		ElectricityPayment electricityPayment = new ElectricityPayment();
		electricityPayment.setName("december");
		electricityPayment.setSensorValues(100);
		electricityPayment.setDate(LocalDate.of(2019,06,18));
						
		repo.save(electricityPayment);
		
		//when
		ElectricityPayment found = repo.findByName("december");
		
		//then
		assertThat(electricityPayment.getName().equals(found.getName()));
		
	}

}
