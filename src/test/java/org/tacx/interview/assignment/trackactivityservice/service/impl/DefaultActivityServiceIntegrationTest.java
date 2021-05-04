package org.tacx.interview.assignment.trackactivityservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tacx.interview.assignment.trackactivityservice.TestActivities;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.trackactivityservice.repository.ActivityRepository;
import org.tacx.interview.assignment.trackactivityservice.service.ActivityService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration testing for {@link DefaultActivityService}. Service --> Repository -->
 * Database
 */
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
		// Given

		// When:
		Activity expectedActivity = TestActivities.createActivity("Morning Ride",
				"Cycling");
		Optional<Activity> actualActivity = activityService
				.createActivity(expectedActivity);
		// Then
		assertThat(actualActivity.get()).isEqualTo(expectedActivity);
		assertThat(actualActivity.get().getName()).isEqualTo("Morning Ride");
		assertThat(actualActivity.get().getType()).isEqualTo("Cycling");
	}

	@Test
	void givenActivityOne_thencreateActivityOne_thenInsertNewRecords() {
		// Given:
		Optional<Activity> expectedActivity = activityService.createActivity(
				TestActivities.createActivityWithRecords("Morning Ride", "Cycling", 2));

		// When:
		Activity newActivity = TestActivities.createActivityWithRecords("Morning Ride",
				"Cycling", 3);
		newActivity.setStartTime(expectedActivity.get().getStartTime());
		Optional<Activity> actualActivity = activityService.createActivity(newActivity);

		// Then:
		assertThat(expectedActivity.get().getRecords().size())
				.isNotEqualTo(newActivity.getRecords().size());

	}

	@Test
	void getActivityById() {
		// Given:
		Optional<Activity> expectedActivity1 = activityService
				.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService
				.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		// When:
		Optional<Activity> actualActivity = activityService
				.getActivityById(expectedActivity1.get().getActivityId());

		// Then:
		assertThat(actualActivity.get()).isEqualTo(expectedActivity1.get());
	}

	@Test
	void getActivityByInvalidId_thenReturnNull() {
		// Given:
		Optional<Activity> expectedActivity1 = activityService
				.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService
				.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		// When:
		Optional<Activity> actualActivity = activityService.getActivityById(3);

		// Then:
		assertThat(actualActivity.isPresent()).isEqualTo(false);
	}

	@Test
	void deleteActivity() {
		// Given:
		Optional<Activity> expectedActivity1 = activityService
				.createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
		Optional<Activity> expectedActivity2 = activityService
				.createActivity(TestActivities.createActivity("Evening Ride", "Cycling"));

		// When:
		activityService.deleteActivity(expectedActivity1.get().getActivityId());
		// Then:
		assertThat(activityService.getAllActivities().size()).isEqualTo(1);
	}

	/*
	 * allowing new records to upload
	 */
	/*
	 * @Test void givenActivityOne_thencreateActivityOne_thenThrowException() { // Given:
	 * Optional<Activity> expectedActivity = activityService
	 * .createActivity(TestActivities.createActivity("Morning Ride", "Cycling"));
	 *
	 * // When and Then Activity newActivity =
	 * TestActivities.createActivity("Morning Ride", "Cycling");
	 * newActivity.setStartTime(expectedActivity.get().getStartTime());
	 * assertThrows(ActivityAlreadyExistsException.class, () -> {
	 * activityService.createActivity(newActivity); });
	 *
	 * }
	 */

}
