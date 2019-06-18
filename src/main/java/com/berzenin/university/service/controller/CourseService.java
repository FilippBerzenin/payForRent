package com.berzenin.university.service.controller;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.CourseRepository;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.exception.NotFoundException;

@Service
public class CourseService extends GenericServiceImpl<Course, CourseRepository> {
	
	public CourseService(CourseRepository repository) {
		super(repository);
	}
	
	public Course ifCoursePresentByName(String name) {
		return repository.findBySubjectContaining(name).orElseThrow(NotFoundException::new);
	}
}
