package com.berzenin.university.service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.berzenin.university.dao.GroupRepository;
import com.berzenin.university.dao.StudentRepository;
import com.berzenin.university.model.persons.Student;
import com.berzenin.university.web.exception.NotFoundException;

@Service
public class StudentService extends GenericServiceImpl<Student, StudentRepository> {
	
	private final GroupRepository groupRepository;
	
	public StudentService(StudentRepository repository, GroupRepository groupRepository) {
		super(repository);
		this.groupRepository=groupRepository;
	}

	public List<Student> findAll(long id) {
		return repository.findByGroupId(id);
	}
	
	public Student addStudent (Student student, long group_id) {
		student.setGroup(groupRepository.findById(group_id).get());
		if (!this.ifStudentPresent(student)) {
			return repository.saveAndFlush(student);
		} else {
			throw new NotFoundException();
		}
	}
	
	public Student updateStudent (Student student) {
		if (!this.ifStudentPresent(student)) {
			Student updateStudent = new Student();
			updateStudent = this.findById(student.getId());
			updateStudent.setName(student.getName());
			updateStudent.setSurename(student.getName());
			updateStudent.setGroup(groupRepository.findByName(student.getGroup().getName()).get());
			return repository.saveAndFlush(updateStudent);
		} else {
			throw new NotFoundException();
		}
	}
	
	public boolean ifStudentPresent (Student student) {
			 if (repository.findByNameAndSurenameAndGroupName(
					 student.getName(), 
					 student.getSurename(), 
					 student.getGroup().getName())
					 .isPresent())
			 {
				 return true;
			 }
			 return false;
	}
	
	public Optional<Student> findByNameAndSurename(String name, String surename) {
		return repository.findByNameAndSurename(name, surename);
		
	}
}
