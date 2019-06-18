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

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class CourseRestEndPointIntegrationTest extends RestIntegrationTest {

	@Before
	public void setUp() {
		assertThat(subject).isNotNull();
		
	}

	@Test
	public void testGetAllCourses() throws Exception {
		// Given
		when(courseService.findAll()).thenReturn(
				Arrays.asList(
				new Course(0L, "first", null, null, null), 
				new Course(1L, "second", null, null, null))
				);
		// Then
		subject.perform(get("/api/course")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(0))
				.andExpect(jsonPath("$[0].subject").value("first"))
				.andExpect(jsonPath("$[1].id").value(1))
				.andExpect(jsonPath("$[1].subject").value("second"));
		// When
		verify(courseService).findAll();
	}

	@Test
	public void testFindCourseById() throws Exception {
		// Given
		Long id = 1L;
		Course course = new Course(id, "first", null, null, null);
		when(courseService.findById(id)).thenReturn(course);
		// Then
		subject.perform(get("/api/course/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.subject").value("first"));
		// When
		verify(courseService).findById(id);
	}

	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 2L;
		when(courseService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/api/course/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Items Not Found")));
		// When
		verify(courseService).findById(id);
	}

	@Test
	public void testUpdateCourse() throws Exception {
		// Given
		Long id = 1L;
		Course updataedCourse = new Course(id, "first", null, null, null);
		when(courseService.update(any())).thenReturn(updataedCourse);
		// Then
		subject.perform(put("/api/course/")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(updataedCourse)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.subject").value("first"))
				.andReturn();
		// When
		verify(courseService).update(updataedCourse);
	}

	@Test
	public void testAddNewCourse() throws Exception {
		// Given
		Long id = 1L;
		Course courseForAdd = new Course(id, "first", null, null, null);
		when(courseService.add(any())).thenReturn(courseForAdd);
		// Then
		subject.perform(post("/api/course")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(courseForAdd)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.subject").value("first"));
		// When
		verify(courseService).add(courseForAdd);
	}

	@Test
	public void testDeleteCourseByEntity() throws Exception {
		// Given
		Long id = 1L;
		Course courseForRemove = new Course(id, "first", null, null, null);
		doNothing().when(courseService).remove(courseForRemove);
		// Then
		subject.perform(delete("/api/course")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(courseForRemove)))
				.andDo(print())
				.andExpect(status().isOk());
		// When
		verify(courseService).remove(courseForRemove);
	}

	@Test
	public void testDeleteCourseById() throws Exception {
		// Given
		Long id = 1L;
		Course courseForRemove = new Course(id, "first", null, null, null);
		when(courseService.findById(id)).thenReturn(courseForRemove);
		// Then
		subject.perform(delete("/api/course/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.subject").value("first"));
		// When
		verify(courseService).findById(id);
	}
}
