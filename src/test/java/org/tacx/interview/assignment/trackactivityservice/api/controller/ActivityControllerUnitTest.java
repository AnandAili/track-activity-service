package org.tacx.interview.assignment.trackactivityservice.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.TestActivities;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.fileprocessor.CSVToEntityProcessor;
import org.tacx.interview.assignment.trackactivityservice.service.impl.DefaultActivityService;
import org.tacx.interview.assignment.trackactivityservice.storage.impl.FileSystemStorageService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

/**
 * Unit testing for Activity Controller alone.
 */
@ActiveProfiles("test")
@WebMvcTest(controllers = { ActivityController.class })
class ActivityControllerUnitTest {

	@MockBean
	DefaultActivityService activityService;

	@MockBean
	CSVToEntityProcessor csvToEntityProcessor;

	@MockBean
	FileSystemStorageService storageService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void createActivity() throws Exception {
		// Given:
		given(csvToEntityProcessor.getActivityFromCSV(any(MultipartFile.class)))
				.willReturn(TestActivities.createActivityWithRecords("Morning Ride",
						"Cycling", 2));
		willDoNothing().given(storageService).store(any(MultipartFile.class));

		// When:
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.csv",
				"text/plain", "activty_def,name,type,start_time,".getBytes());
		mockMvc.perform(multipart("/activities/").file(multipartFile))
				.andExpect(status().isCreated());
		// Then:
		verify(csvToEntityProcessor, times(1)).getActivityFromCSV(multipartFile);
		verify(storageService, times(1)).store(multipartFile);

	}

	@Test
	void getActivity() throws Exception {
		// Given:
		given(activityService.getActivityById(anyInt())).willReturn(Optional.of(
				TestActivities.createActivityWithRecords("Morning Ride", "Cycling", 2)));
		// When:
		mockMvc.perform(get("/activities/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalDistance", is(203)))
				.andExpect(jsonPath("$.totalDuration", is("3 min:0 sec")))
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
		mockMvc.perform(get("/activities/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Activity is not found: 2")));
		// Then:
		verify(activityService, times(1)).getActivityById(2);
	}

	@Test
	void getAllActivities() throws Exception {
		// Given:
		List<Activity> expectedActivityList = List.of(TestActivities
				.createActivityWithRecords("Morning Exercise", "Exercise", 3));
		given(activityService.getAllActivities()).willReturn(expectedActivityList);
		// When:
		mockMvc.perform(get("/activities/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].totalDistance", is(306)))
				.andExpect(jsonPath("$[0].totalDuration", is("8 min:0 sec")))
				.andExpect(jsonPath("$[0].averagePower", is(82.0)))
				.andExpect(jsonPath("$[0].averageCadence", is(72.0)));
		// Then:
		verify(activityService, times(1)).getAllActivities();
	}

	@Test
	void deleteActivity() throws Exception {
		// Given
		given(activityService.deleteActivity(anyInt())).willReturn(Optional.of(
				TestActivities.createActivityWithRecords("Morning Ride", "Cycling", 4)));
		// When
		mockMvc.perform(delete("/activities/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalDistance", is(410)));
		// Then
		verify(activityService, times(1)).deleteActivity(1);
	}

}
