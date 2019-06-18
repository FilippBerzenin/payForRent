package com.berzenin.university.web.mvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.IntegrationTest;
import com.berzenin.university.web.dto.TimetableRequest;

@RunWith(SpringRunner.class)
public class TimetableViewIntegrationTest extends IntegrationTest  {
	
	private Exercise first;
	private Exercise second;
	
	@Before
	public void setUp() {
		// Given
		first = new Exercise(0L, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		second = new Exercise(1L, "second", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
	}
	
	@Test
	public void findAllExercisesBetweenDatesForStudentTest() throws Exception {
		// Given
		TimetableRequest timetableRequest = new TimetableRequest(0L, "Student", "Student", LocalDate.of(2019, 02, 20), LocalDate.of(2019, 02, 20));
		when(timetableService.findAllExercisesBetweenDatesForStudent(timetableRequest)).thenReturn(Arrays.asList(first, second));
		// Then
		subject.perform(post("/timetable/createRequest/student")
			.flashAttr("entityFor", timetableRequest))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(forwardedUrl("timetable"))
			.andExpect(view().name("timetable"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(timetableService).findAllExercisesBetweenDatesForStudent(timetableRequest);

		List<Exercise> exercises = timetableService.findAllExercisesBetweenDatesForStudent(timetableRequest);
		assertThat(exercises.get(0).getId(), is(0L));
		assertThat(exercises.get(1).getId(), is(1L));
		assertThat(exercises.get(0).getName(), is("first"));
		assertThat(exercises.get(1).getName(), is("second"));
		assertThat(exercises.get(0).getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exercises.get(1).getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exercises.get(0).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(0).getTimeFinish(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeFinish(), is(LocalTime.of(12, 00)));
	}
	
	@Test
	public void findAllExercisesBetweenDatesForTeacherTest() throws Exception {
		// Given
		TimetableRequest timetableRequest = new TimetableRequest(0L, "Student", "Student", LocalDate.of(2019, 02, 20), LocalDate.of(2019, 02, 20));
		when(timetableService.findAllExercisesBetweenDatesForTeacher(timetableRequest)).thenReturn(Arrays.asList(first, second));
		// Then
		subject.perform(post("/timetable/createRequest/teacher")
			.flashAttr("entityFor", timetableRequest))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(forwardedUrl("timetable"))
			.andExpect(view().name("timetable"))
 			.andExpect(model().attributeExists("listOfEntites"));
		// When
		verify(timetableService).findAllExercisesBetweenDatesForTeacher(timetableRequest);

		List<Exercise> exercises = timetableService.findAllExercisesBetweenDatesForTeacher(timetableRequest);
		assertThat(exercises.get(0).getId(), is(0L));
		assertThat(exercises.get(1).getId(), is(1L));
		assertThat(exercises.get(0).getName(), is("first"));
		assertThat(exercises.get(1).getName(), is("second"));
		assertThat(exercises.get(0).getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exercises.get(1).getDate(), is(LocalDate.of(2019, 7, 15)));
		assertThat(exercises.get(0).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeBegin(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(0).getTimeFinish(), is(LocalTime.of(12, 00)));
		assertThat(exercises.get(1).getTimeFinish(), is(LocalTime.of(12, 00)));
	}

}
