package com.berzenin.payforrent.model.payment;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ElectricityPayment extends Payment{
	
	public ElectricityPayment (int sensorValues) {
		this.sensorValues = sensorValues;
		this.name = "Payment foe electricity";
	}
}
