package com.berzenin.university.web.mvc;

import static org.hamcrest.CoreMatchers.is;
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

import com.berzenin.university.model.university.Course;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class CourseViewIntegrationTest extends IntegrationTest {

	@Test
	public void getCoursesListTest() throws Exception {
		// Given
		when(courseService.findAll()).thenReturn(Arrays.asList(
				new Course(0L, "first", null, null, null),
				new Course(1L, "second", null, null, null)));
		// Then
		subject.perform(get("/courses/show/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("courses"))
			.andExpect(view().name("courses"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(courseService).findAll();

		List<Course> courses = courseService.findAll();
		assertThat(courses.get(0).getId(), is(0L));
		assertThat(courses.get(1).getId(), is(1L));
		assertThat(courses.get(0).getSubject(), is("first"));
		assertThat(courses.get(1).getSubject(), is("second"));
	}
	
	@Test
	public void findByIdTest() throws Exception {
		// Given
		long id = 1L;
		Course course = new Course(id, "course", null, null, null);
		when(courseService.findById(id)).thenReturn(course);
		// Then
		subject.perform(get("/courses/show/{id}", id))
		.andDo(print())
		.andExpect(forwardedUrl("courses"))
		.andExpect(view().name("courses"))
		.andExpect(status().isOk());
		// When
//		verify(courseService).findById(id);
		Course courseFind = courseService.findById(id);
		assertThat(courseFind.getId(), is(id));
		assertThat(courseFind.getSubject(), is("course"));
	}
	
	@Test
	public void notFindById() throws Exception {
		// Given
		Long id = 1L;
		when(courseService.findById(id)).thenThrow(new NotFoundException());
		// Then
		subject.perform(get("/courses/show/{id}", id))
				.andDo(print())
				.andExpect(view().name("courses"))
				.andExpect(status().isOk());
		// When
//		verify(courseService).findById(id);
	}
	
	@Test
	public void addNewTeacherTest() throws Exception {
		// Given
		long id = 1L;
		Course course = new Course(id, "course", null, null, null);
		when(courseService.add(course)).thenReturn(course);
		// Then
		subject.perform(post("/courses/create/")
			.flashAttr("entity", course))
			.andDo(print())
			.andExpect(forwardedUrl("courses"))
			.andExpect(view().name("courses"))
			.andExpect(status().isCreated());
		// When
		verify(courseService).add(course);
		Course courseFind = courseService.add(course);
		assertThat(courseFind.getId(), is(id));
		assertThat(courseFind.getSubject(), is("course"));
	}
	
	@Test
	public void updateTeacherTest() throws Exception {
		// Given
		long id = 1L;
		Course course = new Course(id, "course", null, null, null);
		when(courseService.update(course)).thenReturn(course);
		// Then
		subject.perform(post("/courses/update/")
			.flashAttr("entityFor", course))
			.andDo(print())
			.andExpect(forwardedUrl("courses"))
			.andExpect(view().name("courses"))
			.andExpect(status().isOk());
		// When
//		verify(courseService).update(course);
		Course courseFind = courseService.update(course);
		assertThat(courseFind.getId(), is(id));
		assertThat(courseFind.getSubject(), is("course"));
	}
	
	@Test
	public void deleteById() throws Exception {
		// Given
		long id = 1L;
		Course course = new Course(id, "course", null, null, null);
		when(courseService.findById(id)).thenReturn(course);
		// Then
		subject.perform(post("/courses/delete/{id}", id))
			.andDo(print())
			.andExpect(view().name("courses"))
			.andExpect(status().isOk());
		// When
		verify(courseService).removeById(id);
	}

}
