package com.berzenin.university.web.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimetableRequest {	
	
	private long id;
	
	private String name;
	
	private String surename;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateStartSearch;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateFinishSearch;
	
	public TimetableRequest(Long id, String name, String surename, LocalDate dateStartSearch, LocalDate dateFinishSearch) {
		this.id=id;
		this.name=name;
		this.surename=surename;
		this.dateStartSearch = dateStartSearch;
		this.dateFinishSearch = dateFinishSearch;
	}

	public TimetableRequest() {
	}

}
