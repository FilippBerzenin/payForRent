package com.berzenin.payforrent.service.controller;

import java.util.List;

import javax.validation.Valid;

public interface GenericService<E> {

	public List<E> findAll();

	public E findById(Long id);

	public E add(E entity);

	public void remove(E entity);

	public void removeById(Long id);

	public E update(@Valid E entity);
}

