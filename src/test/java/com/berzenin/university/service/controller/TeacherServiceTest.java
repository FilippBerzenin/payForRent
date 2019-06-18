package com.berzenin.university.service.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.berzenin.university.dao.TeacherRepository;
import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class TeacherServiceTest {
	
	private Teacher first;
	private Teacher second;
	
	private TeacherService subject;
	
	@Mock
	private TeacherRepository teacherRepository;
	
	@Mock
	private CourseService courseService;
	
	@Before
	public void setUp() {
		// Given
		subject = new TeacherService(teacherRepository, courseService);
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		set.add(course);
		first = new Teacher(id, "name", "surename", set);
		second = new Teacher(id, "names", "surenames", set);
	}

	
	@Test
	public void findAllTeachersTest () {
		//Given
		List<Teacher> teachers = Arrays.asList(first,	second);
		when(teacherRepository.findAll()).thenReturn(teachers);
		// When
		List<Teacher> actual = subject.findAll();
		assertThat(actual, is(teachers));
		assertThat(actual, hasItem(first));
		assertThat(actual, hasItems(second, first));
		assertThat(actual, not(hasItem(new Teacher())));
		assertThat(actual, hasSize(2));
		assertNotEquals(actual, not(hasItem(first)));
		//Then
		verify(teacherRepository, times(1)).findAll();
	}
	
	@Test
	public void findTeachersByIdTest() {
		// Given
		when(teacherRepository.findById(first.getId())).thenReturn(Optional.of(first));
		// When
		assertThat(subject.findById(first.getId()), is(first));
		//Then
		verify(teacherRepository).findById(first.getId());
	}
	
	@Test(expected = NotFoundException.class)
	public void searchTeachersByNameTestNotFoundException() {
		//Given
		when(teacherRepository.findById(first.getId())).thenThrow(new NotFoundException());
		// When
		subject.findById(first.getId());
		//Then
		verify(teacherRepository).findById(first.getId());
	}
	
	@Test
	public void addNewTeachersTest() {
		// Given
		when(teacherRepository.findById(any())).thenReturn(Optional.of(first));
		when(teacherRepository.save(first)).thenReturn(first);
		// When
		assertThat(subject.update(first), is(first));
		//Then
		verify(teacherRepository).save(first);
	}
	
	@Test
	public void deleteTeachersById () {
		// Given
		// When	  
		subject.removeById(first.getId());
		//Then  
	    verify(teacherRepository).deleteById(first.getId());
	}
	
	@Test
	public void deleteTeachersByTeachers () {
		// Given
		// When	  
		subject.remove(first);
		//Then  
	    verify(teacherRepository).delete(first);
	}
	public void addNewCourseForTeachersTest() {
		// Given
		Course course = new Course(1L, "Course", null, null, null);
		when(courseService.ifCoursePresentByName("Course")).thenReturn(course);
		when(teacherRepository.findById(first.getId())).thenReturn(Optional.of(first));
		when(subject.findAll()).thenReturn(Arrays.asList(first));
		//When
		assertThat(subject.addNewCourseForTeacher(0L, course), is(first));
		//Then
		verify(teacherRepository).save(first);
	}
	
	public void removeNewCourseForTeachersTest() {
		// Given
		Course course = new Course(1L, "Course", null, null, null);
		when(courseService.ifCoursePresentByName("Course")).thenReturn(course);
		when(teacherRepository.findById(first.getId())).thenReturn(Optional.of(first));
		when(subject.findAll()).thenReturn(Arrays.asList(first));
		//When
		assertThat(subject.removeCourseFromTeacher(0L, course), is(first));
		//Then
		verify(teacherRepository).save(first);
	}
}
