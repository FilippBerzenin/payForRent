package com.berzenin.university.web.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExerciseRestEndPointIntegrationTest extends RestIntegrationTest {

	@Before
	public void setUp() {
		assertThat(subject).isNotNull();
		
	}

	@Test
	public void testGetAllCourses() throws Exception {		
		// Given
		when(exerciseService.findAll()).thenReturn(
				Arrays.asList(
				new Exercise(0L, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null), 
				new Exercise(1L, "second", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null)
				));
		// Then
		subject.perform(get("/api/excercise")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(0))
				.andExpect(jsonPath("$[0].name").value("first"))
				.andExpect(jsonPath("$[0].date").value("2019-01-15"))
				.andExpect(jsonPath("$[0].timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$[0].timeFinish").value("12:00:00"))
				.andExpect(jsonPath("$[1].id").value(1))
				.andExpect(jsonPath("$[1].name").value("second"))
				.andExpect(jsonPath("$[1].date").value("2019-01-15"))
				.andExpect(jsonPath("$[1].timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$[1].timeFinish").value("12:00:00"))
				;
		// When
		verify(exerciseService).findAll();
	}

	@Test
	public void testFindCourseById() throws Exception {
		// Given
		Long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null);
		when(exerciseService.findById(id)).thenReturn(exercise);
		// Then
		subject.perform(get("/api/excercise/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.date").value("2019-01-15"))
				.andExpect(jsonPath("$.timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$.timeFinish").value("12:00:00"));
		// When
		verify(exerciseService).findById(id);
	}

	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 2L;
		when(exerciseService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/api/excercise/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Items Not Found")));
		// When
		verify(exerciseService).findById(id);
	}

	@Test
	public void testUpdateCourse() throws Exception {
		// Given
		Long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null);
		when(exerciseService.update(any())).thenReturn(exercise);
		// Then
		subject.perform(put("/api/excercise/")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(exercise)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.date").value("2019-01-15"))
				.andExpect(jsonPath("$.timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$.timeFinish").value("12:00:00"))
				.andReturn();
		// When
		verify(exerciseService).update(exercise);
	}

	@Test
	public void testAddNewCourse() throws Exception {
		// Given
		Long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null);
		when(exerciseService.add(any())).thenReturn(exercise);
		// Then
		subject.perform(post("/api/excercise")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(exercise)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.date").value("2019-01-15"))
				.andExpect(jsonPath("$.timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$.timeFinish").value("12:00:00"));
		// When
		verify(exerciseService).add(exercise);
	}

	@Test
	public void testDeleteCourseByEntity() throws Exception {
		// Given
		Long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null);
		doNothing().when(exerciseService).remove(exercise);
		// Then
		subject.perform(delete("/api/excercise")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(exercise)))
				.andDo(print())
				.andExpect(status().isOk());
		// When
		verify(exerciseService).remove(exercise);
	}

	@Test
	public void testDeleteCourseById() throws Exception {
		// Given
		Long id = 1L;
		Exercise exercise = new Exercise(id, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null);
		when(exerciseService.findById(id)).thenReturn(exercise);
		// Then
		subject.perform(delete("/api/excercise/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.date").value("2019-01-15"))
				.andExpect(jsonPath("$.timeBegin").value("12:00:00"))
				.andExpect(jsonPath("$.timeFinish").value("12:00:00"));
		// When
		verify(exerciseService).findById(id);
	}
}
