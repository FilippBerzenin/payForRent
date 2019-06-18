package com.berzenin.university.web.mvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class ExercsieViewIntegrationTest extends IntegrationTest   {
	
	@Test
	public void getExercsieListTest() throws Exception {
		// Given
		when(exerciseService.findAll()).thenReturn(Arrays.asList(
				new Exercise(0L, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null),
				new Exercise(1L, "second", LocalDate.of(2019, 8, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null)));
		// Then
		subject.perform(get("/exercises/show/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("exercises"))
			.andExpect(view().name("exercises"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(exerciseService).findAll();

		List<Exercise> exercises = exerciseService.findAll();
		assertThat(exercises.get(0).getId(), is(0L));
		assertThat(exercises.get(1).getId(), is(1L));
		assertThat(exercises.get(0).getName(), is("first"));
		assertThat(exercises.get(1).getName(), is("second"));
		assertThat(exercises.get(0).getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exercises.get(1).getDate(), is(LocalDate.of(2019, 8, 15)));
		assertThat(exercises.get(0).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(0).getTimeFinish(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeFinish(), is(LocalTime.of(12, 00)));
	}
	
	@Test
	public void findByIdTest() throws Exception {
		// Given
		long id = 1L;
		Exercise exercise = new Exercise(0L, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		when(exerciseService.findById(any())).thenReturn(exercise);
		// Then
		subject.perform(get("/exercises/show/{id}", id))
		.andDo(print())
		.andExpect(forwardedUrl("exercises"))
		.andExpect(view().name("exercises"))
		.andExpect(status().isOk());
		// When
		verify(exerciseService).findById(any());
		Exercise exerciseFind = exerciseService.findById(id);
		assertThat(exerciseFind.getId(), is(0L));
		assertThat(exerciseFind.getName(), is("first"));
		assertThat(exerciseFind.getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exerciseFind.getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exerciseFind.getTimeFinish(), is(LocalTime.of(12, 00)));
	}
	
	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 1L;
		when(exerciseService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/exercises/show/{id}", id))
				.andDo(print())
				.andExpect(view().name("exercises"))
				.andExpect(status().isOk());
		// When
//		verify(exerciseService).findById(id);
	}
	
	@Test
	public void addNewExerciseTest() throws Exception {
		// Given
		long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		when(exerciseService.add(exercise)).thenReturn(exercise);
		// Then
		subject.perform(post("/exercises/create/")
			.flashAttr("entity", exercise))
			.andDo(print())
			.andExpect(forwardedUrl("exercises"))
			.andExpect(view().name("exercises"))
			.andExpect(status().isCreated());
		// When
		verify(exerciseService).add(exercise);
		Exercise exerciseFind = exerciseService.add(exercise);
		assertThat(exerciseFind.getId(), is(id));
		assertThat(exerciseFind.getName(), is("first"));
		assertThat(exerciseFind.getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exerciseFind.getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exerciseFind.getTimeFinish(), is(LocalTime.of(12, 00)));
	}
	
	@Test
	public void updateExerciseTest() throws Exception {
		// Given
		long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		when(exerciseService.update(exercise)).thenReturn(exercise);
		// Then
		subject.perform(post("/exercises/update/")
			.flashAttr("entityFor", exercise))
			.andDo(print())
			.andExpect(forwardedUrl("exercises"))
			.andExpect(view().name("exercises"))
			.andExpect(status().isOk());
		// When
//		verify(exerciseService).update(exercise);
		Exercise exerciseFind = exerciseService.update(exercise);
		assertThat(exerciseFind.getId(), is(id));
		assertThat(exerciseFind.getName(), is("first"));
		assertThat(exerciseFind.getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exerciseFind.getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exerciseFind.getTimeFinish(), is(LocalTime.of(12, 00)));
	}
	
	@Test
	public void deleteById() throws Exception {
		// Given
		long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		when(exerciseService.findById(id)).thenReturn(exercise);
		// Then
		subject.perform(post("/exercises/delete/{id}", id))
			.andDo(print())
			.andExpect(view().name("exercises"))
			.andExpect(status().isOk());
		// When
		verify(exerciseService).removeById(id);
	}
	
	@Test
	public void addNewCourse() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		set.add(course);
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), set);
		when(exerciseService.addNewCourseForExercise(id, course)).thenReturn(exercise);
		when(exerciseService.findAll()).thenReturn(Arrays.asList(exercise));
		// Then
		subject.perform(post("/exercises/addCourse/{id}", id)
			.flashAttr("course", course))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("exercises"))
			.andExpect(view().name("exercises"));
		// When
		verify(exerciseService).addNewCourseForExercise(id, course);
	}
	
	@Test
	public void removeCourse() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), set);
		when(exerciseService.removeCourseFromExercise(id, course)).thenReturn(exercise);
		when(exerciseService.findAll()).thenReturn(Arrays.asList(exercise));
		// Then
		subject.perform(post("/exercises/removeCourse/{id}", id)
			.flashAttr("course", course))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("exercises"))
			.andExpect(view().name("exercises"));
		// When
		verify(exerciseService).removeCourseFromExercise(id, course);
	}

}
