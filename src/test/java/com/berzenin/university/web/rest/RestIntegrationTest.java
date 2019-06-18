package com.berzenin.university.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.berzenin.university.web.IntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class RestIntegrationTest extends IntegrationTest {

	@Autowired
	ObjectMapper mapper;
}
