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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.model.university.Course;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.model.university.Group;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class TeacherViewintegrationTest extends IntegrationTest {
	
	@Test
	public void addNewCourse() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		set.add(course);
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.addNewCourseForTeacher(id, course)).thenReturn(teacher);
		when(teacherService.findAll()).thenReturn(Arrays.asList(teacher));
		// Then
		subject.perform(post("/teachers/addCourse/{id}", id)
			.flashAttr("course", course))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("teachers"))
			.andExpect(view().name("teachers"));
		// When
		verify(teacherService).addNewCourseForTeacher(id, course);
	}
	
	@Test
	public void removeCourse() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		set.add(course);
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.removeCourseFromTeacher(id, course)).thenReturn(teacher);
		when(teacherService.findAll()).thenReturn(Arrays.asList(teacher));
		// Then
		subject.perform(post("/teachers/removeCourse/{id}", id)
			.flashAttr("course", course))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("teachers"))
			.andExpect(view().name("teachers"));
		// When
		verify(teacherService).removeCourseFromTeacher(id, course);
	}
	
	@Test
	public void getTeachersListTest() throws Exception {
		// Given
		when(teacherService.findAll()).thenReturn(Arrays.asList(
				new Teacher(0, "first", "first", null),
				new Teacher(1, "second", "second", null)));
		// Then
		subject.perform(get("/teachers/show/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("teachers"))
			.andExpect(view().name("teachers"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(teacherService).findAll();

		List<Teacher> teachers = teacherService.findAll();
		assertThat(teachers.get(0).getId(), is(0L));
		assertThat(teachers.get(1).getId(), is(1L));
		assertThat(teachers.get(0).getName(), is("first"));
		assertThat(teachers.get(1).getName(), is("second"));
		assertThat(teachers.get(0).getSurename(), is("first"));
		assertThat(teachers.get(1).getSurename(), is("second"));
	}
	
	@Test
	public void findByIdTest() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", null, null, null);
		set.add(course);
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.findById(id)).thenReturn(teacher);
		// Then
		subject.perform(get("/teachers/show/{id}", id))
		.andDo(print())
		.andExpect(forwardedUrl("teachers"))
		.andExpect(view().name("teachers"))
		.andExpect(status().isOk());
		// When
		verify(teacherService).findById(any());
		Teacher teacherFind = teacherService.findById(id);
		assertThat(teacherFind.getId(), is(id));
		assertThat(teacherFind.getName(), is("name"));
		assertThat(teacherFind.getSurename(), is("surename"));
	}
	
	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 1L;
		when(teacherService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/teachers/show/{id}", id))
				.andDo(print())
				.andExpect(view().name("teachers"))
				.andExpect(status().isOk());
		// When
		verify(teacherService).findById(any());
	}
	
	@Test
	public void addNewTeacherTest() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.add(teacher)).thenReturn(teacher);
		// Then
		subject.perform(post("/teachers/create/")
			.flashAttr("entity", teacher))
			.andDo(print())
			.andExpect(forwardedUrl("teachers"))
			.andExpect(view().name("teachers"))
			.andExpect(status().isCreated());
		// When
		verify(teacherService).add(teacher);
		Teacher teacherFind = teacherService.add(teacher);
		assertThat(teacherFind.getId(), is(id));
		assertThat(teacherFind.getName(), is("name"));
		assertThat(teacherFind.getSurename(), is("surename"));
	}
	
	@Test
	public void updateTeacherTest() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Course course = new Course(id, "course", new HashSet<Exercise>(), new HashSet<Group>(), new HashSet<Teacher>());
		set.add(course);
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.update(teacher)).thenReturn(teacher);
		when(teacherService.findAll()).thenReturn(Arrays.asList(
				new Teacher(0, "first", "first", null),
				new Teacher(1, "second", "second", null)));
		// Then
		subject.perform(post("/teachers/update/")
			.flashAttr("entityFor", teacher))
			.andDo(print())
			.andExpect(forwardedUrl("teachers"))
			.andExpect(view().name("teachers"))
			.andExpect(status().isOk());
		// When
//		verify(teacherService).update(teacher);
		Teacher teacherFind = teacherService.update(teacher);
		assertThat(teacherFind.getId(), is(id));
		assertThat(teacherFind.getName(), is("name"));
		assertThat(teacherFind.getSurename(), is("surename"));
	}
	
	@Test
	public void deleteById() throws Exception {
		// Given
		long id = 1L;
		Set<Course> set = new HashSet<>();
		Teacher teacher = new Teacher(id, "name", "surename", set);
		when(teacherService.findById(id)).thenReturn(teacher);
		// Then
		subject.perform(post("/teachers/delete/{id}", id))
			.andDo(print())
			.andExpect(view().name("teachers"))
			.andExpect(status().isOk());
		// When
		verify(teacherService).removeById(id);
	}

}
