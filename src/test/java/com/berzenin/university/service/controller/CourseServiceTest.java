package com.berzenin.university.service.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.berzenin.university.dao.CourseRepository;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class CourseServiceTest {
	
	private CourseService subject;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Before
	public void setUp() {
		subject = new CourseService(courseRepository);
	}
	
	@Test
	public void findAllCoursesTest () {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		Course second = new Course(1L, "second", null, null, null);
		List<Course> courses = Arrays.asList(first,	second);
		when(courseRepository.findAll()).thenReturn(courses);
		//When
		List<Course> actual = (List<Course>) subject.findAll();
		//Then
		assertThat(actual, is(courses));
		assertThat(actual, hasItem(first));
		assertThat(actual, hasItems(second, first));
		assertThat(actual, not(hasItem(new Course())));
		assertThat(actual, hasSize(2));
		assertNotEquals(actual, not(hasItem(first)));
		verify(courseRepository, times(1)).findAll();
	}
	
	@Test
	public void findByIdTest() {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		when(courseRepository.findById(first.getId())).thenReturn(Optional.of(first));
		//When
		assertThat(subject.findById(first.getId()), is(first));
		//Then
		verify(courseRepository).findById(first.getId());
	}
	
	@Test(expected = NotFoundException.class)
	public void searchCourseByNameTestNotFoundException() {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		when(courseRepository.findById(first.getId())).thenThrow(new NotFoundException());
		//When
		subject.findById(first.getId());
		//Then
		verify(courseRepository).findById(first.getId());
	}
	
	@Test
	public void addNewCourseTest() {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		when(courseRepository.save(first)).thenReturn(first);
		//When
		assertThat(subject.update(first), is(first));
		//Then
		verify(courseRepository).save(first);
	}
	
	@Test
	public void deleteCourseById () {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		//When
		subject.removeById(first.getId());
		//Then
	    verify(courseRepository).deleteById(first.getId());
	}
	
	@Test
	public void deleteCourseByCourse () {
		//Given
		Course first = new Course(0L, "first", null, null, null);
		//When
		subject.remove(first);
		//Then
	    verify(courseRepository).delete(first);
	}
	
	public void ifCoursePresentByNameTest () {
		//Given
		String courseName = "first";
		Course first = new Course(0L, "first", null, null, null);
		when(courseRepository.findBySubjectContaining(courseName)).thenReturn(Optional.of(first));
		//When
		assertThat(subject.ifCoursePresentByName(courseName), is(first));
		//Then	  
	    verify(courseRepository).findBySubjectContaining(courseName);
	}
}
