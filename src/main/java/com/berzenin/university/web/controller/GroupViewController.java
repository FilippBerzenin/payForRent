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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.service.controller.GroupService;
import com.berzenin.university.web.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/groups")
public class GroupViewController extends GenericViewControllerImpl<Group, GroupService> {
	
	public 	GroupViewController(GroupService service) {
		page = "groups";
	}
	
	@ModelAttribute("course")
	public Course getCourseForm () {
		return new Course();
	}

	@RequestMapping(value="/search", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String searchGroupByName(@RequestParam String filter, Model model) {
 		if (filter != null && !filter.isEmpty()) {
 			try {
 				entites = service.searchByName(filter);
 				message = "The group was found";
 				setModelAttribute(model);
 			} catch (NotFoundException e) {
 				message = "The group wasn't found";
 				return page;
 			} finally {
 				setModelAttribute(model);	
 			} 			
 		} else {
 			setModelAttribute(model);
 	 		message = "Wrong parametr for search";
 	 	}
 		return page;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addNewGroup(@RequestParam String newGroupsName, Model model) {
		try {
			if (newGroupsName != null && newGroupsName.length()>0 && service.addNewGroup(newGroupsName)) {
				message = "New group sucessful created";
				entites = service.findAll();
				}
			return page;
		} catch (RuntimeException e) {
			log.info("group was not created" + e);
			message = newGroupsName+ " group was not created.";
			return page;
		} finally {
			setModelAttribute(model);
		}
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String update(@PathVariable("id") Long id, @RequestParam(value="newGroupName") String newGroupName, Model model) {
		try {
			if (newGroupName != null && newGroupName.length()>0) {
				service.updateName(id, newGroupName);	
			}
			entites = service.findAll();
			message = "group was sucessful updated";
			return page;
		} catch (RuntimeException e) {
			log.info("group was not updated " + e);
			message = id+ " group was not updated.";
			return page;
		} finally {
			setModelAttribute(model);
		}
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