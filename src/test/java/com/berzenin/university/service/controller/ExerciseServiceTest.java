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
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.berzenin.university.dao.ExerciseRepository;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class ExerciseServiceTest {
	
	private ExerciseService subject;
	
	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Mock
	private CourseService courseService;
	
	private Exercise first;
	
	@Before
	public void setUp() {
		// Given
		subject = new ExerciseService(exerciseRepository, courseService);
		first = new Exercise(0L, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00),  new HashSet<Course>());
	}

	
	@Test
	public void findAllExercisesTest () {
		//Given
		Exercise second = new Exercise(0L, "second", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), new HashSet<Course>());
		List<Exercise> exercises = Arrays.asList(first,	second);
		when(exerciseRepository.findAll()).thenReturn(exercises);
		//When
		List<Exercise> actual = subject.findAll();
		assertThat(actual, is(exercises));
		assertThat(actual, hasItem(first));
		assertThat(actual, hasItems(second, first));
		assertThat(actual, not(hasItem(new Exercise())));
		assertThat(actual, hasSize(2));
		assertNotEquals(actual, not(hasItem(first)));
		//Then
		verify(exerciseRepository, times(1)).findAll();
	}
	
	@Test
	public void findByIdTest() {
		// Given
		when(exerciseRepository.findById(first.getId())).thenReturn(Optional.of(first));
		//When
		assertThat(subject.findById(first.getId()), is(first));
		//Then
		verify(exerciseRepository).findById(first.getId());
	}
	
	@Test(expected = NotFoundException.class)
	public void searchExerciseByNameTestNotFoundException() {
		//Given
		when(exerciseRepository.findById(first.getId())).thenThrow(new NotFoundException());
		//When
		assertThat(subject.findById(first.getId()), is(new NotFoundException()));
		//Then
		verify(exerciseRepository).findById(first.getId());
	}
	
	@Test
	public void addNewExerciseTest() {
		//Given
		when(exerciseRepository.save(first)).thenReturn(first);
		//When
		assertThat(subject.add(first), is(first));
		//Then
		verify(exerciseRepository).save(first);
	}
	
	@Test
	public void updateExerciseTest() {
		//Given
		Course course = new Course(0L, "Course", null, null, null);
		first.getCourses().add(course);
		when(exerciseRepository.findById(first.getId())).thenReturn(Optional.of(first));
		when(exerciseRepository.save(first)).thenReturn(first);
		//When
		assertThat(subject.update(first), is(first));
		//Then
		verify(exerciseRepository).save(first);
	}
	
	@Test
	public void deleteExerciseById () {
		//Given
		//When
		subject.removeById(first.getId());
		//Then 
	    verify(exerciseRepository).deleteById(first.getId());
	}
	
	@Test
	public void deleteCourseByExercise () {
		//Given
		//When
		subject.remove(first);
		//Then  
	    verify(exerciseRepository).delete(first);
	}
	
	public void addNewCourseForExerciseTest() {
		// Given
		Course course = new Course(1L, "Course", null, null, null);
		when(courseService.ifCoursePresentByName(any())).thenReturn(course);
		when(exerciseRepository.findById(any())).thenReturn(Optional.of(first));
		when(exerciseRepository.save(any())).thenReturn(first);
		//When
		assertThat(subject.addNewCourseForExercise(0L, course), is(first));
		//Then
		verify(exerciseRepository).save(first);
	}
	
	public void removeNewCourseForExerciseTest() {
		// Given
		Course course = new Course(1L, "Course", null, null, null);
		when(courseService.ifCoursePresentByName("Course")).thenReturn(course);
		when(exerciseRepository.findById(first.getId())).thenReturn(Optional.of(first));
		//When
		assertThat(subject.removeCourseFromExercise(0L, course), is(first));
		//Then
		verify(exerciseRepository).save(first);
	}
}
