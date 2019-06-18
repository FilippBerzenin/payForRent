package com.berzenin.university.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class GroupServiceTest extends IntegrationTest{
	
	@Test
	public void findAllGroupTest() {
		//Given
		Group first = new Group(1L, "First");
		Group second = new Group(2L, "Second");
		List<Group> groups = Arrays.asList(first, second);
		when(groupRepository.findAll()).thenReturn(groups);
		//Then
		assertThat(groupRepository.findAll(), is(groups));
		assertThat(groupRepository.findAll(), hasItem(first));
		assertThat(groupRepository.findAll(), hasItems(second, first));
		assertThat(groupRepository.findAll(), not(hasItem(new Group())));
		assertNotEquals(groupRepository.findAll(), not(hasItem(first)));
		assertThat(groupRepository.findAll(), hasSize(2));
	}
	
	@Test
	public void addNewGroupTest() {
		// Given
		Group first = new Group(1L, "First");
		String newGroupsName = "First";
		when(groupRepository.findByName(newGroupsName)).thenReturn(Optional.of(new Group("Empty")));
		when(groupRepository.saveAndFlush(first)).thenReturn(first);
		when(groupService.add(first)).thenReturn(first);
		//Then
		assertThat(groupRepository.findByName(newGroupsName).get()
				.getName().equals("Empty"));
		assertThat(groupService.add(first), is(first));
		// When
		verify(groupService).add(first);
	}
	
	@Test
	public void searchGroupsByNameTest() {
		//Given
		Group first = new Group(1L, "First");
		List<Group> groups = Arrays.asList(first);
		when(groupService.searchByName(first.getName())).thenReturn(groups);
		//Then
		assertThat(groupService.searchByName(first.getName()), is(groups));
		//When
		verify(groupService).searchByName(first.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void searchGroupsByNameTestNotFoundException() {
		//Given
		Group first = new Group(1L, "First");
		when(groupService.searchByName(first.getName())).thenThrow(new NotFoundException());
		//Then
		groupService.searchByName(first.getName());
		// When
		verify(groupService).searchByName(first.getName());
	}
	
	@Test
	public void findByIdTest() {
		// Given
		Group first = new Group(1L, "First");
		when(groupService.findById(first.getId())).thenReturn(first);
		//Then
		assertThat(groupService.findById(first.getId()), is(first));
		// When
		verify(groupService).findById(first.getId());
	}
	
	@Test (expected = NotFoundException.class)
	public void notFindByIdTest() {
		// Given
		when(groupService.findById(2L)).thenThrow(new NotFoundException());
		//Then
			groupService.findById(2L);
		// When
		verify(groupService).findById(2L);
	}
	
	@Test
	public void deleteGroupByGroup () {
		// Given
		Group first = new Group(1L, "First");
		//Then
	    groupService.remove(first);
		// When	  
	    verify(groupService).remove(first);
	}
	
	@Test
	public void deleteGroupById () {
		// Given
		long id = 1L;
		//Then
	    groupService.removeById(id);
		// When	  
	    verify(groupService).removeById(1L);
	}

}
