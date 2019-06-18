package com.berzenin.university.web.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
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

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class StudentRestEndpointIntegrationTest extends RestIntegrationTest {

	@Before
	public void setUp() {
		assertThat(subject).isNotNull();
	}
	
	@Test	
	public void testGetAllGroups () throws Exception {
		// Given
		when(groupService.findAll()).thenReturn(Arrays.asList(
				new Group(1, "test", null, null),
				new Group(2, "second", null, null)));
		// Then
		subject.perform(get("/api/groups"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id").value(1))
		.andExpect(jsonPath("$[0].name").value("test"))
		.andExpect(jsonPath("$[1].id").value(2))
		.andExpect(jsonPath("$[1].name").value("second"));
		// When
		verify(groupService).findAll();
	}


	@Test
	public void testGetAllStudents() throws Exception {
		// Given
		when(studentService.findAll())
				.thenReturn(Arrays.asList(
						new Student(1, "Alex", "Ro"), 
						new Student(2, "Mary", "Bo")));
		// Then
		subject.perform(get("/api/students"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Alex"))
				.andExpect(jsonPath("$[0].surename").value("Ro"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Mary"))
				.andExpect(jsonPath("$[1].surename").value("Bo"));
		// When
		verify(studentService).findAll();
	}
	
	@Test
	public void testAddStudent() throws Exception {
		// Given
		Student studentAdd = new Student(2, "Some", "Name");
		when(studentService.add(any())).thenReturn(studentAdd);
		// Then
		subject.perform(post("/api/students")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(studentAdd))
				)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Some"))
				.andExpect(jsonPath("$.surename").value("Name"));
		// When
		verify(studentService).add(studentAdd);
	}
	
	@Test
	public void testUpdateStudent() throws Exception {
		// Given
		Long id = 1L;
		Student studentForUpdate = new Student(id, "Filipp", "Berzenin");
		Student studentWithOldParam = new Student(id, "Filipp", "Berz");
		when(studentService.findById(id)).thenReturn(studentWithOldParam);
		when(studentService.update(any())).thenReturn(studentForUpdate);
		// Then
		subject.perform(put("/api/students/"+id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(studentForUpdate)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("Filipp"))
				.andExpect(jsonPath("$.surename").value("Berzenin"))
				.andReturn();
		// When
		verify(studentService).findById(id);
		verify(studentService).update(studentForUpdate);
	}

	@Test
	public void testFindStudentById() throws Exception {
		// Given
		Long id = 1L;
		when(studentService.findById(any())).thenReturn(new Student(id, "Fil", "Berzenin", null));
		// Then
		subject.perform(get("/api/students/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("Fil"))
				.andExpect(jsonPath("$.surename").value("Berzenin"));
		// When
		verify(studentService).findById(id);
	}

	@Test()
	public void notFindById() throws Exception {
		// Given
		Long id = 2L;
		when(studentService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/api/students/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Items Not Found")));
		// When
		verify(studentService).findById(id);
	}

	@Test
	public void testDeleteStudentsById() throws Exception {
		// Given
		Long id = 1L;
		Student studentForDelete = new Student(1, "Tima", "Berzenin");
		when(studentService.findById(id)).thenReturn(studentForDelete);
		// Then
		subject.perform(delete("/api/students/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk());
		// When
		verify(studentService).findById(id);
	}
}