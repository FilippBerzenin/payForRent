package com.berzenin.university.web.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.dto.TimetableRequest;

@RunWith(SpringRunner.class)
public class TimetableRestEndPointIntegrationTest extends RestIntegrationTest {
	
	@Before
	public void setUp() {
		assertThat(subject).isNotNull();
	}
	
	@Test
	public void createRequestForStudentTest() throws Exception {
		Long id = 1L;
		TimetableRequest timetableRequest = new TimetableRequest(id, "first", "first", LocalDate.of(2019, 1, 15), LocalDate.of(2019, 1, 15));
		when(timetableService.findAllExercisesBetweenDatesForStudent(timetableRequest)).thenReturn(
				Arrays.asList(
				new Exercise(0L, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null), 
				new Exercise(1L, "second", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null)
				));
		subject.perform(get("/api/timetable/student")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(timetableRequest)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//		.andExpect(jsonPath("$[0].id").value(0))
//		.andExpect(jsonPath("$[0].name").value("first"))
//		.andExpect(jsonPath("$[0].date").value("2019-01-15"))
//		.andExpect(jsonPath("$[0].timeBegin").value("12:00:00"))
//		.andExpect(jsonPath("$[0].timeFinish").value("12:00:00"))
//		.andExpect(jsonPath("$[1].id").value(1))
//		.andExpect(jsonPath("$[1].name").value("second"))		
//		.andExpect(jsonPath("$[1].date").value("2019-01-15"))
//		.andExpect(jsonPath("$[1].timeBegin").value("12:00:00"))
//		.andExpect(jsonPath("$[1].timeFinish").value("12:00:00"))
		.andReturn();
	}
	
	@Test
	public void createRequestForTeacherTest() throws Exception {
		Long id = 1L;
		TimetableRequest timetableRequest = new TimetableRequest(id, "first", "first", LocalDate.of(2019, 1, 15), LocalDate.of(2019, 1, 15));
		when(timetableService.findAllExercisesBetweenDatesForTeacher(timetableRequest)).thenReturn(
				Arrays.asList(
				new Exercise(0L, "first", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null), 
				new Exercise(1L, "second", LocalDate.of(2019, 1, 15), LocalTime.of(12, 0), LocalTime.of(12, 0), null)
				));
		subject.perform(get("/api/timetable/teacher")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(mapper.writeValueAsBytes(timetableRequest)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//		.andExpect(jsonPath("$[0].id").value(0))
//		.andExpect(jsonPath("$[0].name").value("first"))
//		.andExpect(jsonPath("$[0].date").value("2019-01-15"))
//		.andExpect(jsonPath("$[0].timeBegin").value("12:00:00"))
//		.andExpect(jsonPath("$[0].timeFinish").value("12:00:00"))
//		.andExpect(jsonPath("$[1].id").value(1))
//		.andExpect(jsonPath("$[1].name").value("second"))		
//		.andExpect(jsonPath("$[0].date").value("2019-01-15"))
//		.andExpect(jsonPath("$[0].timeBegin").value("12:00:00"))
//		.andExpect(jsonPath("$[0].timeFinish").value("12:00:00"))
		.andReturn();
	}

}
