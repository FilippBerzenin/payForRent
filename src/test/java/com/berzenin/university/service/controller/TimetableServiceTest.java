package com.berzenin.university.service.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.berzenin.university.dao.ExerciseRepository;
import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.persons.Teacher;
import com.berzenin.university.model.university.Exercise;
import com.berzenin.university.web.dto.TimetableRequest;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class TimetableServiceTest {
	
	private TimetableService subject;
	
	@Mock
	private ExerciseRepository exerciseRepository;
	@Mock
	private StudentService studentRepository;
	@Mock
	private TeacherService teacherRepository;

	private ExerciseService exerciseService;
	
	private Exercise first;
	private Exercise second;
	
	@Before
	public void setUp() {
		// Given
		exerciseService = new ExerciseService(exerciseRepository, null);
		subject = new TimetableService(exerciseRepository, exerciseService, studentRepository, teacherRepository);
		first = new Exercise(0L, "first", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
		second = new Exercise(0L, "second", LocalDate.of(2019, 7, 15), LocalTime.of(12, 00), LocalTime.of(12, 00), null);
	}
	
	@Test
	public void findAllExercisesBetweenDatesForStudentTest() {
		//Given
		TimetableRequest timetableRequest = new TimetableRequest(0L, "Student", "Student", LocalDate.of(2019, 02, 20), LocalDate.of(2019, 02, 20));
		Student student = new Student(0L,"Student", "Student");
		List<Exercise> exercises = Arrays.asList(first,	second);
		when(studentRepository.findByNameAndSurename(any(), any())).thenReturn(Optional.of(student));
		when(exerciseRepository.findByCourses_Groups_Students_IdAndDateBetween(
				student.getId(), 
				timetableRequest.getDateStartSearch(), 
				timetableRequest.getDateFinishSearch()))
		.thenReturn(exercises);
		//When
		assertThat(subject.findAllExercisesBetweenDatesForStudent(timetableRequest), is(exercises));
		//Then
		verify(exerciseRepository).findByCourses_Groups_Students_IdAndDateBetween(
				student.getId(), 
				timetableRequest.getDateStartSearch(), 
				timetableRequest.getDateFinishSearch());
	}
	
	@Test
	public void findAllExercisesBetweenDatesForTeacherTest() {
		//Given
		TimetableRequest timetableRequest = new TimetableRequest(0L, "Student", "Student", LocalDate.of(2019, 02, 20), LocalDate.of(2019, 02, 20));
		Teacher teacher = new Teacher(0L,"Student", "Student", null);
		List<Exercise> exercises = Arrays.asList(first,	second);
		when(teacherRepository.findByNameAndSurename(any(), any())).thenReturn(Optional.of(teacher));
		when(exerciseRepository.findByCourses_Teacher_IdAndDateBetween(
				teacher.getId(), 
				timetableRequest.getDateStartSearch(), 
				timetableRequest.getDateFinishSearch()))
		.thenReturn(exercises);
		//When
		assertThat(subject.findAllExercisesBetweenDatesForTeacher(timetableRequest), is(exercises));
		//Then
		verify(exerciseRepository).findByCourses_Teacher_IdAndDateBetween(
				teacher.getId(), 
				timetableRequest.getDateStartSearch(), 
				timetableRequest.getDateFinishSearch());
	}

}
