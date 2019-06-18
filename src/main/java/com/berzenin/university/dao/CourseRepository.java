package com.berzenin.university.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.university.model.university.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
	
	Optional<Course> findBySubjectContaining(String subject);

}
