package com.berzenin.university.web.mvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class GroupViewIntegrationTest extends IntegrationTest {

	@Test
	public void getGroupsListTest() throws Exception {
		// Given
		when(groupService.findAll()).thenReturn(Arrays.asList(new Group(1, "first"), new Group(2, "second")));
		// Then
		subject.perform(get("/groups/show/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("groups"))
			.andExpect(view().name("groups"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(groupService).findAll();

		List<Group> groups = groupService.findAll();
		assertThat(groups.get(0).getId(), is(1L));
		assertThat(groups.get(1).getId(), is(2L));
		assertThat(groups.get(0).getName(), is("first"));
		assertThat(groups.get(1).getName(), is("second"));
	}
	
	@Test
	public void findGroupByName() throws Exception {
		//Given
		long id = 1L;
		String name = "test";
		Group group = new Group(id, name);
		when(groupService.searchByName(name)).thenReturn(Arrays.asList(group));
		when(groupService.findAll()).thenReturn(Arrays.asList(group));
		//Then
		subject.perform(post("/groups/search")
			.param("filter", name))
			.andDo(print())
			.andExpect(forwardedUrl("groups"))
			.andExpect(view().name("groups"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("listOfEntites"))
			.andExpect(model().attribute("listOfEntites", hasSize(1)));
		//When
			verify(groupService).searchByName(name);
	}
	
	@Test
	public void notFindById() throws Exception {
		// Given
		String name = "test";
		when(groupService.searchByName(name)).thenThrow(new NotFoundException());
		// Then
		subject.perform(post("/groups/search")
				.param("filter", "test"))
				.andExpect(forwardedUrl("groups"))
				.andExpect(view().name("groups"))
				.andExpect(status().isOk());
		// When
		verify(groupService).searchByName(name);
	}
	
	@Test
	public void addNewGroupTest() throws Exception {
		// Given
		String newGroupsName = "First";
		when(groupService.addNewGroup(newGroupsName)).thenReturn(true);
		// Then
		subject.perform(post("/groups/create")
			.param("newGroupsName", newGroupsName))
			.andDo(print())
			.andExpect(forwardedUrl("groups"))
			.andExpect(view().name("groups"))
			.andExpect(status().isCreated());
		// When
		verify(groupService).addNewGroup(newGroupsName);
	}

	@Test
	public void deleteById() throws Exception {
		// Given
		Long id = 1L;
		Group groupsForDelete = new Group(id, "test", null, null);
		when(groupService.findById(id)).thenReturn(groupsForDelete);
		// Then
		subject.perform(post("/groups/delete/{id}", id))
			.andDo(print())
			.andExpect(view().name("groups"))
			.andExpect(status().isOk());
		// When
		verify(groupService).removeById(id);
	}

	@Test
	public void updateGroupTest() throws Exception {
		// Given
		Long id = 1L;
		String newName = "First";
		Group groupForUpdate = new Group(id, "First", null, null);
		Group groupWithOldParam = new Group(id, "Fir", null, null);
		when(groupService.findById(id)).thenReturn(groupWithOldParam);
		when(groupService.update(groupForUpdate)).thenReturn(groupForUpdate);
		when(groupService.findAll()).thenReturn(Arrays.asList(groupForUpdate));
		// Then
		subject.perform(post("/groups/update/{id}", id)
				.param("newGroupName", newName))
		.andDo(print())
				.andExpect(forwardedUrl("groups"))
				.andExpect(view().name("groups"))
				.andExpect(status().isOk());
		// When
		verify(groupService).findAll();
	}
}
