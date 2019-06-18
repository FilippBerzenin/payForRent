package com.berzenin.university.service.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.GroupRepository;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.exception.NotFoundException;

@Service
public class GroupService extends GenericServiceImpl<Group, GroupRepository> {
	
	private final CourseService courseService;

	public GroupService(GroupRepository repository, CourseService courseService) {
		super(repository);
		this.courseService=courseService;
	}

	public boolean addNewGroup(String newGroupsName) {
		if (repository.findByName(newGroupsName).isPresent()) {
			throw new NotFoundException();
		}
			this.update(Group.builder().name(newGroupsName).build());
			return true;
	}
	
	public List<Group> searchByName (String nameFoSearch) {
		return repository.findByNameContaining(nameFoSearch);
	}

	public Group updateName(long id, String newGroupName) {
		Group group = this.findById(id);
		group.setName(newGroupName);
		return this.update(group);
	}
	
	public Group addNewCourseForTeacher(Long groupId, Course course) {
		try {
			Course courseForAdd = courseService.ifCoursePresentByName(course.getSubject());
			Group group = repository.findById(groupId).orElseThrow(NotFoundException::new);
			group.getCourses().add(courseForAdd);
			repository.save(group);
			return group;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
	
	public Group removeCourseFromTeacher(Long groupId, Course course) {
		try {
			Course removeCourse = courseService.ifCoursePresentByName(course.getSubject());
			Group group = repository.findById(groupId).orElseThrow(NotFoundException::new);
			group.getCourses().remove(removeCourse);
			repository.save(group);
			return group;
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}
}
