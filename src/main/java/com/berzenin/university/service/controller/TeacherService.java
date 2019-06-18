package com.berzenin.university.service.controller;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.TeacherRepository;
import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.exception.NotFoundException;

@Service
public class TeacherService extends GenericServiceImpl<Teacher, TeacherRepository> {
	
	private final CourseService courseService;

	public TeacherService(TeacherRepository repository, CourseService courseService) {
		super(repository);
		this.courseService=courseService;
	}
	
	@Override
	public Teacher update(Teacher entity) {
		Teacher entityForUpdate = repository.findById(entity.getId()).get();
		entityForUpdate.setName(entity.getName());
		entityForUpdate.setSurename(entity.getSurename());
		return repository.save(entityForUpdate);
	}
	
	public Teacher addNewCourseForTeacher(Long teacherId, Course course) {
		try {
			Course courseForAdd = courseService.ifCoursePresentByName(course.getSubject());
			Teacher teacher = repository.findById(teacherId).orElseThrow(NotFoundException::new);
			teacher.getCourses().add(courseForAdd);
			repository.save(teacher);
			return teacher;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
	
	public Teacher removeCourseFromTeacher(Long teacherId, Course course) {
		try {
			Course removeCourse = courseService.ifCoursePresentByName(course.getSubject());
			Teacher teacher = repository.findById(teacherId).orElseThrow(NotFoundException::new);
			teacher.getCourses().remove(removeCourse);
			repository.save(teacher);
			return teacher;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
	
	public Optional<Teacher> findByNameAndSurename(String name, String surename) {
		return repository.findByNameAndSurename(name, surename);		
	}
}
