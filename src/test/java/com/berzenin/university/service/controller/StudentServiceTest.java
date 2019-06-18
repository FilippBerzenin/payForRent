package com.berzenin.university.service.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class StudentServiceTest extends IntegrationTest{
	
	@Test
	public void findAllGroupTest() {
		//Given
		Student first = new Student(1L, "First", "First");
		Student second = new Student(2L, "Second", "Second");
		List<Student> students = Arrays.asList(first, second);
		when(studentService.findAll(1L)).thenReturn(students);
		//Then
		assertThat(studentService.findAll(1L), is(students));
		assertThat(studentService.findAll(1L), hasItem(first));
		assertThat(studentService.findAll(1L), hasItems(second, first));
		assertThat(studentService.findAll(1L), not(hasItem(new Student())));
		assertNotEquals(studentService.findAll(1L), not(hasItem(first)));
		assertThat(studentService.findAll(1L), hasSize(2));
	}
	
	@Test
	public void addNewStudentTest() {
		//Given		
		Group group = new Group(1L, "Group");
		Student first = new Student(1L, "First", "First", group);
		when(studentService.ifStudentPresent(first)).thenReturn(false);
		when(studentRepository.saveAndFlush(first)).thenReturn(first);
		when(studentService.addStudent(first, group.getId())).thenReturn(first);
		when(studentRepository.findByNameAndSurenameAndGroupName(first.getName(), first.getSurename(), first.getGroup().getName())).thenReturn(Optional.empty());
		//Then
		assertThat(studentService.addStudent(first, group.getId()), is(first));
		assertThat(studentRepository.saveAndFlush(first), is(first));
		//When
		verify(studentService).addStudent(first, group.getId());
		verify(studentRepository).saveAndFlush(first);
	}
	
	@Test
	public void ifStudentPresentTest() {
		//Given
		Group group = new Group(1L, "Group");
		Student first = new Student(1L, "First", "First", group);
		when(studentService.ifStudentPresent(first)).thenReturn(true);
		//Then
		assertThat(studentService.ifStudentPresent(first), is(true));
		//When
		verify(studentService).ifStudentPresent(first);
	}

	@Test
	public void findByIdTest() {
		// Given
		Group group = new Group(1L, "Group");
		Student first = new Student(1L, "First", "First", group);
		when(studentService.findById(first.getId())).thenReturn(first);
		//Then
		assertThat(studentService.findById(first.getId()), is(first));
		// When
		verify(studentService).findById(first.getId());
	}
	
	@Test (expected = NotFoundException.class)
	public void notFindByIdTest() {
		// Given
		when(studentService.findById(2L)).thenThrow(new NotFoundException());
		//Then
		studentService.findById(2L);
		// When
		verify(studentService).findById(2L);
	}
	
	@Test
	public void deleteStudentById () {
		// Given
		long id = 1;
		//Then
		studentService.removeById(id);
		// When
		verify(studentService).removeById(1L);
	}
}
