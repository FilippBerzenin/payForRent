package com.berzenin.university.web.restсontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.service.controller.StudentService;

@RestController
@RequestMapping(value="/api/students")
public class StudentController extends GenericControllerImpl<Student, StudentService> {	
	
	public StudentController(StudentService service) {
		super(service);
	}

	@PutMapping(
			value = "/{id}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Student updateStudent(@RequestBody Student student, @PathVariable("id") long id) {
		Student studentForUpdate = service.findById(id);
		studentForUpdate.setName(student.getName());
		studentForUpdate.setSurename(student.getSurename());
		return service.update(studentForUpdate);
	}
}
