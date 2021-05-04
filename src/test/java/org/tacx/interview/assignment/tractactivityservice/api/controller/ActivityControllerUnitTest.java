package org.tacx.interview.assignment.tractactivityservice.api.controller;

import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.tacx.interview.assignment.tractactivityservice.TestActivities;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;
import org.tacx.interview.assignment.tractactivityservice.service.ActivityService;
import org.tacx.interview.assignment.tractactivityservice.service.impl.DefaultActivityService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@WebMvcTest(controllers = {ActivityController.class})
class ActivityControllerUnitTest {

	@MockBean
	DefaultActivityService activityService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void createActivity() {
	}

  @Test
  void getActivity() throws Exception {
    // Given:
    given(activityService.getActivityById(anyInt()))
        .willReturn(
            Optional.of(TestActivities.createActivityWithRecords("Morning Ride", "Cycling", 2)));
    // When:
    mockMvc
        .perform(get("/activities/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalDistance", is(203)))
		.andExpect(jsonPath("$.totalDuration", is("3:0")))
		.andExpect(jsonPath("$.averagePower", is(81.5)))
		.andExpect(jsonPath("$.averageCadence", is(71.5)));
    // Then:
    verify(activityService, times(1)).getActivityById(1);
  }

  @Test
  void getInValidActivity_thenError_404() throws Exception {
    // Given:
    given(activityService.getActivityById(2)).willReturn(Optional.empty());
    // When:
    mockMvc
        .perform(get("/activities/2").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Activity is not found: 2")));
    // Then:
    verify(activityService, times(1)).getActivityById(2);
  }

	@Test
	void getAllActivities() throws Exception {
		//Given:
		List<Activity> expectedActivityList = List.of(TestActivities.createActivityWithRecords("Morning Exercise", "Exercise", 3));
		given(activityService.getAllActivities()).willReturn(expectedActivityList);
		//When:
		mockMvc.perform(get("/activities/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].totalDistance", is(306)))
				.andExpect(jsonPath("$[0].totalDuration", is("8:0")))
				.andExpect(jsonPath("$[0].averagePower", is(82.0)))
				.andExpect(jsonPath("$[0].averageCadence", is(72.0)));
		//Then:
		verify(activityService, times(1)).getAllActivities();
	}

  @Test
  void deleteActivity() throws Exception {
    // Given
    given(activityService.deleteActivity(anyInt()))
        .willReturn(Optional.of(TestActivities.createActivityWithRecords("Morning Ride", "Cycling", 4)));
    // When
	  mockMvc.perform(delete("/activities/1").accept(MediaType.APPLICATION_JSON))
			  .andExpect(status().isOk())
			  .andExpect(jsonPath("$.totalDistance", is(410)));
    // Then
	  verify(activityService, times(1)).deleteActivity(1);
  }
}
