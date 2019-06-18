package com.berzenin.university.web.controller;

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

import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.service.controller.TeacherService;

@Controller
@RequestMapping(value="/teachers")
public class TeacherViewController extends GenericViewControllerImpl<Teacher, TeacherService> {
	
	public 	TeacherViewController(TeacherService service) {
		page = "teachers";
	}
	
	@ModelAttribute("entityFor")
	public Teacher getTeacherForm () {
		return new Teacher();
	}
	
	@ModelAttribute("course")
	public Course getCourseForm () {
		return new Course();
	}

	@RequestMapping(value = "/addCourse/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String addNewCourseForTeacher(
			@PathVariable("id") Long teacherId,
			@Valid @ModelAttribute("course") Course course, 
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			message = "Error";
			setModelAttribute(model);
			return page;
		}
		try {
			service.addNewCourseForTeacher(teacherId, course);
			message = "Course was successful added";
			entites = service.findAll();
			setModelAttribute(model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}
	}
	
	@RequestMapping(value = "/removeCourse/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String removeCourseFromTeacher(
			@PathVariable("id") Long teacherId,
			@Valid @ModelAttribute("course") Course course, 
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			message = "Error";
			setModelAttribute(model);
			return page;
		}
		try {
			service.removeCourseFromTeacher(teacherId, course);;
			message = "Course was successful remove";
			entites = service.findAll();
			setModelAttribute(model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}
	}
}
