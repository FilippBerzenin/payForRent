package com.berzenin.university.web.restсontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.service.controller.TeacherService;

@RestController
@RequestMapping(value = "/api/teachers")
public class TeacherController extends GenericControllerImpl<Teacher, TeacherService> {

	public TeacherController(TeacherService service) {
		super(service);
	}
}
