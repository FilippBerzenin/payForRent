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

import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class TeacherRestEndPointIntegrationTest extends RestIntegrationTest  {
	
	@Before
	public void setUp() {
		assertThat(subject).isNotNull();
	}
	
	@Test	
	public void testGetAllTeachers () throws Exception {
		// Given
		when(teacherService.findAll()).thenReturn(Arrays.asList(
				new Teacher(0, "first", "first", null),
				new Teacher(1, "second", "second", null)));
		// Then
		subject.perform(get("/api/teachers"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id").value(0))
		.andExpect(jsonPath("$[0].name").value("first"))
		.andExpect(jsonPath("$[0].surename").value("first"))
		.andExpect(jsonPath("$[1].id").value(1))
		.andExpect(jsonPath("$[1].name").value("second"))
		.andExpect(jsonPath("$[1].surename").value("second"));
		// When
		verify(teacherService).findAll();
	}
	
	@Test
	public void testFindTeacherById () throws Exception {
		// Given
		Long id = 1L;
		Teacher teacher = new Teacher(id, "first", "first", null);
		when(teacherService.findById(id)).thenReturn(teacher);
		// Then
		subject.perform(get("/api/teachers/"+id)
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(id))
		.andExpect(jsonPath("$.name").value("first"))
		.andExpect(jsonPath("$.surename").value("first"))
		;
		// When
		verify(teacherService).findById(id);
	}
	
	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 2L;
		when(teacherService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/api/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Items Not Found")));
		// When
		verify(teacherService).findById(id);
	}
	
	@Test
	public void testUpdateTeacher() throws Exception {
		// Given
		Long id = 1L;
		Teacher updataedTeacher = new Teacher(id, "first", "first", null);
		when(teacherService.update(any())).thenReturn(updataedTeacher);
		// Then
		subject.perform(put("/api/teachers/")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(updataedTeacher)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.surename").value("first"))
				.andReturn();
		// When
		verify(teacherService).update(updataedTeacher);
	}
	
	@Test
	public void testAddNewTeacher() throws Exception {
		// Given
		Long id = 1L;
		Teacher teacherForAdd = new Teacher(id, "first", "first", null);
		when(teacherService.add(any())).thenReturn(teacherForAdd);
		// Then
		subject.perform(post("/api/teachers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(teacherForAdd))
				)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.surename").value("first"));
		// When
		verify(teacherService).add(teacherForAdd);
	}
	
	@Test
	public void testDeleteTeacherByEntity() throws Exception {
		// Given
		Long id = 1L;
		Teacher teacherForRemove = new Teacher(id, "first", "first", null);
		doNothing().when(teacherService).remove(teacherForRemove);
		// Then
		subject.perform(delete("/api/teachers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(teacherForRemove)))
				.andDo(print())
				.andExpect(status().isOk());
		// When
		verify(teacherService).remove(teacherForRemove);
	}
	
	@Test
	public void testDeleteTeacherById() throws Exception {
		// Given
		Long id = 1L;
		Teacher teacherForRemove = new Teacher(id, "first", "first", null);
		when(teacherService.findById(id)).thenReturn(teacherForRemove);
		// Then
		subject.perform(delete("/api/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("first"))
				.andExpect(jsonPath("$.surename").value("first"));
		// When
		verify(teacherService).findById(id);
	}
}
