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
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupRestEndpointIntegrationTest extends RestIntegrationTest  {

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
	public void testAddNewGroup() throws Exception {
		// Given
		Group group = new Group(1L, "first"); 
		when(groupService.add(any())).thenReturn(group);
		// Then
		subject.perform(post("/api/groups")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(group))
				)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("first"));
		// When
		verify(groupService).add(group);
	}
	
	@Test
	public void testFindGroupById () throws Exception {
		// Given
		when(groupService.findById(1L)).thenReturn(new Group("first"));
		// Then
		subject.perform(get("/api/groups/"+1L)
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(0))
		.andExpect(jsonPath("$.name").value("first"));
		// When
		verify(groupService).findById(1L);
	}

	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 2L;
		when(groupService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/api/groups/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Items Not Found")));
		// When
		verify(groupService).findById(id);
	}
	
	@Test
	public void testUpdateGroup() throws Exception {
		// Given
		Long id = 1L;
		Group groupForUpdate = new Group(id, "First", null, null);
		Group groupWithOldParam = new Group(id, "Fir", null, null);
		when(groupService.findById(id)).thenReturn(groupWithOldParam);
		when(groupService.update(any())).thenReturn(groupForUpdate);
		// Then
		subject.perform(put("/api/groups/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(groupForUpdate)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("First"))
				.andReturn();
		// When
		verify(groupService).findById(id);
		verify(groupService).update(groupWithOldParam);
	}
	
	@Test
	public void testGetAllStudentsFromGroup () throws Exception {
		
		Set<Student> students = new HashSet<>();
		students.add(new Student(1L, "Alex", "Ro", new Group(1L, "test", null, null)));
		students.add(new Student(2L, "Mary", "Bo", new Group(1L, "test", null, null)));
		// Given
		when(groupService.findById(1L)).thenReturn(new Group(1L, "test", students, null));
		// Then
		subject.perform(get("/api/groups/"+1L+"/students"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id").value(2))
			.andExpect(jsonPath("$[0].name").value("Mary"))
			.andExpect(jsonPath("$[0].surename").value("Bo"))
			.andExpect(jsonPath("$[1].id").value(1))
			.andExpect(jsonPath("$[1].name").value("Alex"))
			.andExpect(jsonPath("$[1].surename").value("Ro"))
			;
		// When
		verify(groupService).findById(1L);
	}
	
	@Test
	public void testDeleteGroupsById() throws Exception {
		// Given
		Long id = 1L;
		Group groupsForDelete = new Group(id, "test", null, null);
		when(groupService.findById(id)).thenReturn(groupsForDelete);
		// Then
		subject.perform(delete("/api/groups/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		// When
		verify(groupService).findById(id);
	}
}
