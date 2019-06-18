package com.berzenin.university.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.berzenin.university.model.university.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
	
	public List<Exercise> findByCourses_Id(Long id);
	public List<Exercise> findByCourses_Groups_Id(Long Id);
	public List<Exercise> findByCourses_Groups_Name(String name);
	
	public List<Exercise> findByCourses_Groups_Students_IdAndDateBetween(
			Long id, 
			LocalDate publicationTimeStart,
			LocalDate publicationTimeEnd);
	
	public List<Exercise> findByCourses_Teacher_IdAndDateBetween(
			Long id, 
			LocalDate publicationTimeStart,
			LocalDate publicationTimeEnd);
	
	public List<Exercise> findByCourses_Groups_Students_Surename(
			@Param("surename") String surename);

}
