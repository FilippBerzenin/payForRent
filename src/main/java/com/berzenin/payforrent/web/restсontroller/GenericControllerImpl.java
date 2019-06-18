package com.berzenin.payforrent.web.restсontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.berzenin.payforrent.service.controller.GenericService;

public abstract class GenericControllerImpl<E, S extends GenericService<E>> implements GenericController<E> {
	
	protected final S service;

	@Autowired
	public GenericControllerImpl(S service) {
		this.service=service;
	}
	
	@Override
	public List<E> getAll() {
		return service.findAll();
	}	

	@Override
	public E add(@RequestBody E entity) {
		return service.add(entity);
	}
	
	@Override
	public E update(@RequestBody E entity) {
		return service.update(entity);
	}

	@Override
	public E getEntityById(@PathVariable("id") long id) {
		return service.findById(id);
	}

	@Override
	public E delete (@PathVariable("id") long id) {
		E entity = service.findById(id);
		service.remove(entity);
		return entity;
	}
	
	@Override
	public void delete (@RequestBody E entity) {
		service.remove(entity);
	}
}