package com.berzenin.university.service.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.ExerciseRepository;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.dto.TimetableRequest;

@Service
public class TimetableService extends GenericServiceImpl<Exercise, ExerciseRepository> {

	private final StudentService studentService;	
	private final TeacherService teacherService;
	private final ExerciseService exerciseService;

	public TimetableService(ExerciseRepository repository, ExerciseService exerciseService, StudentService studentRepository, TeacherService teacherRepository) {
		super(repository);
		this.exerciseService=exerciseService;
		this.studentService=studentRepository;
		this.teacherService=teacherRepository;
	}

	public List<Exercise> findAllExercisesBetweenDatesForStudent(TimetableRequest student) {
		return exerciseService.findByCourses_Groups_Students_IdAndDateBetween(
				studentService.findByNameAndSurename(student.getName(), student.getSurename()).get().getId(),
				student.getDateStartSearch(), student.getDateFinishSearch());
	}
	
	public List<Exercise> findAllExercisesBetweenDatesForTeacher(TimetableRequest teacher) {
		return exerciseService.findByCourses_Teacher_IdAndDateBetween(
				teacherService.findByNameAndSurename(teacher.getName(), teacher.getSurename()).get().getId(),
				teacher.getDateStartSearch(), teacher.getDateFinishSearch());
	}
}