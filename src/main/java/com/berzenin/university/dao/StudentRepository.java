package com.berzenin.university.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.university.model.persons.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	List<Student> findByGroupId(Long id);
	
	Optional<Student> findByNameAndSurename(String name, String surename);
	
	Optional<Student> findByNameAndSurenameAndGroupName(String name, String surename, String group);
	
}
