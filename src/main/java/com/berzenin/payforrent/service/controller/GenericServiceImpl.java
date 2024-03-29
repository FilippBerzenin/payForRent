package com.berzenin.payforrent.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.berzenin.payforrent.web.exception.NotFoundException;

@Service
public abstract class GenericServiceImpl<E, R extends CrudRepository<E, Long>> implements GenericService<E> {
	
    protected final R repository;

    @Autowired
    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }
	
	@Override
	public List<E> findAll() {
		return (List<E>) repository.findAll();
	}

	@Override
	public E findById(Long id) {
		return repository.findById(id)
				.orElseThrow(NotFoundException::new);
	}

	@Override
	public E add(E entity) {
		return repository.save(entity);
	}
	
	@Override
	public E update(E entity) {
		return repository.save(entity);
	}

	@Override
	public void removeById(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	public void remove(E entity) {
		repository.delete(entity);
	}
}