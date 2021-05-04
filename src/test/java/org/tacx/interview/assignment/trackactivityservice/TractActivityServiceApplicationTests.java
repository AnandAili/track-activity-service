package org.tacx.interview.assignment.trackactivityservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tacx.interview.assignment.trackactivityservice.api.controller.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TractActivityServiceApplicationTests {

	@Autowired
	ActivityController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
