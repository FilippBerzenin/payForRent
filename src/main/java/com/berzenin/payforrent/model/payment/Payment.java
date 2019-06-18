package com.berzenin.payforrent.model.payment;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@NotNull
	@Size(min=1, max=50)
	@Column(name = "name")
	protected String name;
	
	@NotNull
	@Column(name = "sensorValues")
	protected int sensorValues;
	
	@NotNull
	@Column(name = "date")
	private LocalDate date;
	

}
