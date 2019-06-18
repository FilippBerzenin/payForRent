package com.berzenin.university.web.restсontroller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.service.controller.GroupService;

@RestController
@RequestMapping(value="/api/groups")
public class GroupController extends GenericControllerImpl<Group, GroupService> {
	
	public GroupController(GroupService service) {
		super(service);
	}	
	
	@GetMapping(
			value = "/{id}/students",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Set<Student> students(@PathVariable("id") long groupId){
    	return getEntityById(groupId).getStudents();
	}
	
	@PutMapping(
			value = "/{id}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Group updateGroup(@RequestBody Group group, @PathVariable("id") long id) {
		Group groupForUpdate = service.findById(id);
		groupForUpdate.setName(group.getName());
		return service.update(groupForUpdate);
	}
}
