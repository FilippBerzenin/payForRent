package com.berzenin.university.web.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.dto.TimetableRequest;
import com.berzenin.university.service.controller.TimetableService;

@Controller
@RequestMapping(value="/timetable")
public class TimetableViewController extends GenericViewControllerImpl<Exercise, TimetableService> {
	
	public 	TimetableViewController(TimetableService service) {
		page = "timetable";
	}
	
	@ModelAttribute("entityFor")
	public TimetableRequest getLoginForm() {
		return new TimetableRequest();
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String setStartPage () {
		return page;		
	}

	@RequestMapping(value = "/createRequest/student", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String createRequestForStudent (
	@ModelAttribute("entityFor") @Valid TimetableRequest entity,
	BindingResult result, 
	Model model) {
		if (result.hasErrors()) {
			message = "Error";
			setModelAttribute(model);
			return page;
		} try {
			entites = service.findAllExercisesBetweenDatesForStudent(entity);
			message = "Search was finded";
			setModelAttribute(model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}		
	}
	
	@RequestMapping(value = "/createRequest/teacher", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String createRequestForteacher (
	@ModelAttribute("entityFor") @Valid TimetableRequest entity,
	BindingResult result, 
	Model model) {
		if (result.hasErrors()) {
			message = "Error";
			setModelAttribute(model);
			return page;
		} try {
			entites = service.findAllExercisesBetweenDatesForTeacher(entity);
			message = "Search was finded";
			setModelAttribute(model);
			return page;
		} catch (RuntimeException e) {
			this.setModelAttributeWhenthrowException(e, model);
			return page;
		}		
	}
}
