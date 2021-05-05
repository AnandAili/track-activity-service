package org.tacx.interview.assignment.trackactivityservice.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.tacx.interview.assignment.trackactivityservice.api.response.ActivityResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration testing for end to end flow. Controller --> Service --> Repository -->
 * Database
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityControllerIntegrationTest {

	public static final String ASSIGNMENT_ACTIVITY_CSV = "assignment_activity.csv";

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void createActivity() {

		ClassPathResource resource = new ClassPathResource(ASSIGNMENT_ACTIVITY_CSV,
				ActivityControllerIntegrationTest.class.getClassLoader());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("file", resource);
		var requestEntity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity(
				"http://localhost:" + port + "/activities/", requestEntity, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

	@Test
	void getActivity() {
		ResponseEntity<ActivityResponse> activityResponse = this.restTemplate
				.getForEntity("http://localhost:" + port + "/activities/1",
						ActivityResponse.class);
		assertThat(activityResponse).isNotNull();
		assertThat(activityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(activityResponse.getBody().getTotalDistance()).isEqualTo(11990);
		assertThat(activityResponse.getBody().getTotalDuration())
				.isEqualTo("7 min:0 sec");
		assertThat(activityResponse.getBody().getAveragePower()).isEqualTo(165.0);
		assertThat(activityResponse.getBody().getAverageCadence()).isEqualTo(88.5);
	}

	@Test
	void getAllActivities() {
		ParameterizedTypeReference<List<ActivityResponse>> responseType = new ParameterizedTypeReference<List<ActivityResponse>>() {
		};
		ResponseEntity<List<ActivityResponse>> activitiesResponse = this.restTemplate
				.exchange("http://localhost:" + port + "/activities/", HttpMethod.GET,
						null, responseType);
		assertThat(activitiesResponse).isNotNull();
		assertThat(activitiesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void deleteActivity() {
		ResponseEntity<ActivityResponse> deletedActivity = this.restTemplate.exchange(
				"http://localhost:" + port + "/activities/1", HttpMethod.DELETE, null,
				ActivityResponse.class);
		assertThat(deletedActivity).isNotNull();
		assertThat(deletedActivity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

}
