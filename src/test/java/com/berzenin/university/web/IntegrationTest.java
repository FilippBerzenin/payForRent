package com.berzenin.university.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.berzenin.university.dao.CourseRepository;
import com.berzenin.university.dao.ExerciseRepository;
import com.berzenin.university.dao.GroupRepository;
import com.berzenin.university.dao.StudentRepository;
import com.berzenin.university.dao.TeacherRepository;
import com.berzenin.university.service.controller.CourseService;
import com.berzenin.university.service.controller.ExerciseService;
import com.berzenin.university.service.controller.GroupService;
import com.berzenin.university.service.controller.StudentService;
import com.berzenin.university.service.controller.TeacherService;
import com.berzenin.university.service.controller.TimetableService;

@SpringBootTest(classes = UniversityWebServiceTestApplication.class)
@AutoConfigureMockMvc
public class IntegrationTest {
	
	@Autowired
	protected MockMvc subject;

	@MockBean
	protected GroupRepository groupRepository;	
	@MockBean
	protected GroupService groupService;
	
	@MockBean
	protected StudentService studentService;
	@MockBean
	protected StudentRepository studentRepository;
	
	@MockBean
	protected CourseRepository courseRepository;
	@MockBean
	protected CourseService courseService;
	
	
	@MockBean
	protected ExerciseRepository exerciseRepository;
	@MockBean
	protected ExerciseService exerciseService;
	
	@MockBean
	protected TeacherRepository teacherRepository;	
	@MockBean
	protected TeacherService teacherService;
	
	@MockBean
	protected TimetableService timetableService;
}
