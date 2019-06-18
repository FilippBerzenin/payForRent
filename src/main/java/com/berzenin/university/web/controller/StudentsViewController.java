package com.berzenin.university.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.service.controller.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/students")
public class StudentsViewController extends GenericViewControllerImpl<Student, StudentService> {

	public StudentsViewController(StudentService service) {
		page = "students";
	}

	private String message = "Something wrong";
	private List<Student> students;

	@ModelAttribute("studentFor")
	public Student getLoginForm() {
		return new Student();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getAllStudents(@PathVariable("id") Long id, Model model) {
		try {
			message = "All students from group";
			setModelAttribute(id, model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model, id);
			return page;
		}
	}

	@RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String createStudent(@PathVariable("id") Long id, @ModelAttribute("student") @Valid Student student,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			message = "Something wrong with new students name or surename";
			setModelAttribute(id, model);
			return page;
		}
		try {
			service.addStudent(student, id);
			message = "New student created";
			setModelAttribute(id, model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model, id);
			return page;
		}
	}

	@Override
	@RequestMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteEntity(@PathVariable("id") Long id, Model model) {
		try {
			Long group_id = service.findById(id).getGroup().getId();
			service.removeById(id);
			message = "Student deleted";
			setModelAttribute(group_id, model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model, id);
			return page;
		}
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String updateStudentNew(@PathVariable("id") Long id, @ModelAttribute("studentFor") @Valid Student student,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			message = "Something wrong with students name, surename or groups name";
			setModelAttribute(id, model);
			return page;
		}
		try {
			service.updateStudent(student);
			message = "Student was successful update";
			setModelAttribute(id, model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model, id);
			return page;
		}
	}

	private Model setModelAttribute(Long id, Model model) {
		students = service.findAll(id);
		model.addAttribute("message", message);
		model.addAttribute("group_id", id);
		return model.addAttribute("studentsList", students);
	}

	protected void setModelAttributeWhenthrowException(RuntimeException e, Model model, long id) {
		log.error("Error " + e);
		message = "Error " + e;
		students = service.findAll(id);
		setModelAttribute(id, model);
	}
}
