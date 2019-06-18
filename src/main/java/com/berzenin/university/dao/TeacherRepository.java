package com.berzenin.university.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.university.model.persons.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
	
	public Optional<Teacher> findByNameAndSurename(String name, String surename);

}
