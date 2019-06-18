package com.berzenin.payforrent.web.restсontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface GenericController <E> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<E> getAll();

	@GetMapping(
			value = "/{id}", 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public E getEntityById(@PathVariable("id") long id);

	@PutMapping(
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public E update(@RequestBody E entity);

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete (@RequestBody E entity);
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public E delete (@PathVariable("id") long id);
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public E add(@RequestBody E entity);


}