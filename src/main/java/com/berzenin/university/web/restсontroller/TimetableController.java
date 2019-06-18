package com.berzenin.university.web.restсontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.service.controller.TimetableService;
import com.berzenin.university.web.dto.TimetableRequest;

@RestController
@RequestMapping(value="/api/timetable")
public class TimetableController extends GenericControllerImpl<Exercise, TimetableService>  {	

	public TimetableController(TimetableService service) {
		super(service);
	}
	
	@GetMapping(
			value = "/student", 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Exercise> createRequestForStudent(@RequestBody TimetableRequest entity) {
		List<Exercise> exercises = new ArrayList<>();;
		exercises.addAll(service.findAllExercisesBetweenDatesForStudent(entity));
		return exercises;
	}
	
	@GetMapping(
			value = "/teacher", 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Set<Exercise> createRequestForTeacher(@RequestBody TimetableRequest entity) {
		Set<Exercise> exercises = new HashSet<>();;
		exercises.addAll(service.findAllExercisesBetweenDatesForTeacher(entity));
		return exercises;
	}
}
