package org.tacx.interview.assignment.tractactivityservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tacx.interview.assignment.tractactivityservice.TestActivities;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;
import org.tacx.interview.assignment.tractactivityservice.exception.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.tractactivityservice.exception.ActivityNotFoundException;
import org.tacx.interview.assignment.tractactivityservice.repository.ActivityRepository;
import org.tacx.interview.assignment.tractactivityservice.service.ActivityService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class DefaultActivityServiceIntegrationTest {

	@Autowired
	ActivityService activityService;

	@Autowired
	ActivityRepository activityRepository;

	@BeforeEach
	void cleanRepository() {
		activityRepository.deleteAll();
	}

	@Test
	void createActivity() {
		//Given

		//When:
		Activity expectedActivity = TestActivities.createActivity("Morning Ride", "Cycling");
		Optional<Activity> actualActivity = activityService.createActivity(expectedActivity);
		//Then
		assertThat(actualActivity.get()).isEqualTo(expectedActivity);
		assertThat(actualActivity.get().getName()).isEqualTo("Morning Ride");
		assertThat(actualActivity.get().getType()).isEqualTo("Cycling");
	}

	@Test
	void getActivityById() {
		//Given:
		Optional<Activity> expectedActivity1 = activityService.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		//When:
		Optional<Activity> actualActivity = activityService.getActivityById(expectedActivity1.get().getActivityId());

		//Then:
		assertThat(actualActivity.get()).isEqualTo(expectedActivity1.get());
	}

	@Test
	void getActivityByInvalidId_thenReturnNull() {
		//Given:
		Optional<Activity> expectedActivity1 = activityService.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		//When:
		Optional<Activity> actualActivity = activityService.getActivityById(3);

		//Then:
		assertThat(actualActivity.isPresent()).isEqualTo(false);
	}

	@Test
	void deleteActivity() {
		//Given:
		Optional<Activity> expectedActivity1 = activityService.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		//When:
		activityService.deleteActivity(expectedActivity1.get().getActivityId());
		//Then:
		assertThat(activityService.getAllActivities().size()).isEqualTo(1);
	}



	@Test
	void givenActivityOne_thencreateActivityOne_thenThrowException() {
		//Given:
		Optional<Activity> expectedActivity = activityService.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));

		//When and Then
		Activity newActivity = TestActivities.createActivity("Morning Ride", "Cycling");
		assertThrows(ActivityAlreadyExistsException.class, () -> {
			activityService.createActivity(newActivity);
		});


	}
}
