package org.tacx.interview.assignment.trackactivityservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tacx.interview.assignment.trackactivityservice.TestActivities;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.trackactivityservice.repository.ActivityRepository;
import org.tacx.interview.assignment.trackactivityservice.service.ActivityService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

/**
 * Unit testing for DefailtActivityService alone, mocking it's dependencies
 */
@SpringBootTest(classes = { DefaultActivityService.class })
class DefaultActivityServiceUnitTest {

	@Autowired
	ActivityService activityService;

	@MockBean
	ActivityRepository activityRepository;

	@Test
	void givenActivityOne_whenGetActivityOne_thenReturnActivityOne() {
		// Given:
		Activity expectedActivity = TestActivities.createActivity("Morning Ride",
				"Cycling");
		given(activityRepository.findById(any(Integer.class)))
				.willReturn(Optional.of(expectedActivity));

		// When:
		Optional<Activity> actualActivity = activityService.getActivityById(1);

		// Then
		assertThat(actualActivity).isNotEmpty();
		verify(activityRepository, times(1)).findById(1);
	}

	@Test
	void givenActivityOne_whenGetActivityTwo_thenReturnEmptyActivity() {
		// Given: Activity 1
		Activity expectedActivity = TestActivities.createActivity("Morning Ride",
				"Cycling");
		given(activityRepository.findById(1)).willReturn(Optional.of(expectedActivity));
		given(activityRepository.findById(2)).willReturn(Optional.empty());

		// When: Get Activity 2
		Optional<Activity> actualActivity = activityService.getActivityById(2);

		// Then: Returns empty Activity
		assertThat(actualActivity).isEmpty();

	}

	@Test
	void givenActivityOne_whenDeleteActivityOne_thenReturnSuccess() {
		// Given:
		Activity expectedActivity = TestActivities.createActivity("Morning Ride",
				"Cycling");
		given(activityRepository.findById(any(Integer.class)))
				.willReturn(Optional.of(expectedActivity));
		willDoNothing().given(activityRepository).deleteById(anyInt());

		// When:
		activityService.deleteActivity(1);

		// Then:
		verify(activityRepository, times(1)).findById(1);
		verify(activityRepository, times(1)).delete(expectedActivity);
	}

	@Test
	void givenNothing_whenCreateActivityOne_thenCreateActivityOne() {
		// Given:
		Activity expectedActivity = TestActivities.createActivity("Evening Ride",
				"Cycling");
		given(activityRepository.findActivityByNameAndTypeAndStartTime(anyString(),
				anyString(), any(LocalDateTime.class))).willReturn(Optional.empty());
		given(activityRepository.save(any(Activity.class))).willReturn(expectedActivity);
		// When:
		Optional<Activity> createdActivity = activityService
				.createActivity(expectedActivity);
		// Then:
		assertThat(createdActivity.get()).isEqualTo(expectedActivity);
		verify(activityRepository, times(1)).findActivityByNameAndTypeAndStartTime(
				"Evening Ride", "Cycling", createdActivity.get().getStartTime());
		verify(activityRepository, times(1)).save(expectedActivity);
	}

	@Test
	void givenActivityOne_whenTryToCreateActivityOne_thenInsertOnlynewRecords() {

		// Given:
		Activity expectedActivity = TestActivities.createActivity("Evening Ride",
				"Cycling");
		given(activityRepository.findActivityByNameAndTypeAndStartTime(anyString(),
				anyString(), any(LocalDateTime.class)))
						.willReturn(Optional.of(expectedActivity));
		given(activityRepository.save(any(Activity.class))).willReturn(expectedActivity);
		willDoNothing().given(activityRepository).delete(expectedActivity);
		// When:
		Optional<Activity> createdActivity = activityService
				.createActivity(expectedActivity);
		// Then:
		assertThat(createdActivity.get()).isEqualTo(expectedActivity);
		verify(activityRepository, times(1)).findActivityByNameAndTypeAndStartTime(
				"Evening Ride", "Cycling", createdActivity.get().getStartTime());
		verify(activityRepository, times(1)).save(expectedActivity);
		verify(activityRepository, times(1)).delete(expectedActivity);

	}

	/**
	 * commented this test case, because now allowing to insert new records for exisitng
	 * activity
	 */
	/*
	 * @Test void
	 * givenActivityOne_whenTryToCreateActivityOne_thenThrowActivityAlreadyExistException(
	 * ) {
	 *
	 * // Given: Activity expectedActivity = TestActivities.createActivity("Evening Ride",
	 * "Cycling");
	 * given(activityRepository.findActivityByNameAndTypeAndStartTime(anyString(),
	 * anyString(), any(LocalDateTime.class))) .willReturn(Optional.of(expectedActivity));
	 * // When: assertThrows(ActivityAlreadyExistsException.class, () -> {
	 * activityService.createActivity(expectedActivity); }); // Then:
	 * verify(activityRepository, times(1)).findActivityByNameAndTypeAndStartTime(
	 * "Evening Ride", "Cycling", expectedActivity.getStartTime());
	 *
	 * }
	 */

}
