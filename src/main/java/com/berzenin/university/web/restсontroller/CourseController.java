package com.berzenin.university.web.restсontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berzenin.university.model.university.Course;
import com.berzenin.university.service.controller.CourseService;

@RestController
@RequestMapping(value="/api/course")
public class CourseController extends GenericControllerImpl<Course, CourseService>  {
	
	public CourseController(CourseService service) {
		super(service);
	}

}
