package com.berzenin.university.service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.ExerciseRepository;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.exception.NotFoundException;

@Service
public class ExerciseService extends GenericServiceImpl<Exercise, ExerciseRepository> {

	private final CourseService courseService;

	public ExerciseService(ExerciseRepository repository, CourseService courseService) {
		super(repository);
		this.courseService=courseService;
	}	

	public Exercise update(Exercise entity) {
		entity.setCourses(repository.findById(entity.getId()).get().getCourses());
		return repository.save(entity);
	}
	
	public List<Exercise> findByCourses_Teacher_IdAndDateBetween (
			Long id, 
			LocalDate publicationTimeStart,
			LocalDate publicationTimeEnd) {
		return repository.findByCourses_Teacher_IdAndDateBetween(id, publicationTimeStart, publicationTimeEnd);
	}
	
	public List<Exercise> findByCourses_Groups_Students_IdAndDateBetween(
			Long id, 
			LocalDate publicationTimeStart,
			LocalDate publicationTimeEnd) {
		return repository.findByCourses_Groups_Students_IdAndDateBetween(id, publicationTimeStart, publicationTimeEnd);
	}
	
	public Exercise addNewCourseForExercise(Long exerciseId, Course course) {
		try {
			Course courseForAdd = courseService.ifCoursePresentByName(course.getSubject());
			Exercise exercise = repository.findById(exerciseId).orElseThrow(NotFoundException::new);
			exercise.getCourses().add(courseForAdd);
			repository.save(exercise);
			return exercise;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
	
	public Exercise removeCourseFromExercise(Long exerciseId, Course course) {
		try {
			Course removeCourse = courseService.ifCoursePresentByName(course.getSubject());
			Exercise exercise = repository.findById(exerciseId).orElseThrow(NotFoundException::new);
			exercise.getCourses().remove(removeCourse);
			repository.save(exercise);
			return exercise;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
}
