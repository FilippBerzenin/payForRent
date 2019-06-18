package com.berzenin.university.web.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.berzenin.university.web.rest.RestIntegrationTest;

@RunWith(SpringRunner.class)
public class IndexIntegrationTest  extends RestIntegrationTest{
	
	@Test
	public void initIndexView() throws Exception {
		subject.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(forwardedUrl("index"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("message", "Abandon hope all ye who enter here"));
	}
	
}
