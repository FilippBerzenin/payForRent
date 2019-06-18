package com.berzenin.university.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.berzenin.university.model.university.Course;
import com.berzenin.university.service.controller.CourseService;

@Controller
@RequestMapping(value="/courses")
public class CourseViewController extends GenericViewControllerImpl<Course, CourseService>{

	public 	CourseViewController(CourseService service) {
		page = "courses";
	}
	
	@ModelAttribute("entityFor")
	public Course getLoginForm () {
		return new Course();
	}
}
