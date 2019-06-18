package com.berzenin.university.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = PayForRentTestApplication.class)
@AutoConfigureMockMvc
public class IntegrationTest {
	
	@Autowired
	protected MockMvc subject;

}
