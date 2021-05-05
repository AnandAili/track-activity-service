package org.tacx.interview.assignment.trackactivityservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tacx.interview.assignment.trackactivityservice.TestActivities.createActivityWithRecords;

/**
 * Integration testing for JPA Repository. Repository --> Database
 */
@ActiveProfiles("test")
@DataJpaTest
class ActivityRepositoryIntegrationTest {

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void testEmptyRepository() {
		List<Activity> activities = activityRepository.findAll();
		assertThat(activities).isEmpty();
	}

	@Test
	public void testValidGetActivityById() {
		// Given: list of activities
		Activity expectedEveiningRide = entityManager
				.persist(createActivityWithRecords("Eveining Ride", "Cycling", 2));
		entityManager.persist(createActivityWithRecords("Morning Ride", "Cycling", 4));

		// When: fetch an activity details by it's id
		Optional<Activity> actualActivity = activityRepository
				.findById(expectedEveiningRide.getActivityId());

		// Then: return valid activity details
		assertThat(actualActivity.isPresent()).isEqualTo(true);
		assertThat(actualActivity.get()).isEqualTo(expectedEveiningRide);
		assertThat(actualActivity.get().getRecords().size()).isEqualTo(2);
	}

	@Test
	public void testCreateActivityWithRecords_ShouldReturnSuccess() {
		// Given: no activites

		// When: create an activity
		Activity morningRide = activityRepository
				.save(createActivityWithRecords("Morning Ride", "Cycling", 2));

		// Then: activity created successfully
		Activity expectedActivity = entityManager.find(Activity.class,
				morningRide.getActivityId());
		assertThat(expectedActivity).isNotNull();
		assertThat(expectedActivity.getRecords().size()).isEqualTo(2);
	}

	@Test
	public void testListAllActivities_ShouldReturnAllActivities() {

		// Given: list of activities
		Activity expectedEveiningRide = entityManager
				.persist(createActivityWithRecords("Eveining Ride", "Cycling", 2));
		Activity expectedMorningRide = entityManager
				.persist(createActivityWithRecords("Morning Ride", "Cycling", 4));
		entityManager.flush();

		// When: get All activities
		List<Activity> actualActivityList = activityRepository.findAll();

		// Then: should return all activites
		assertThat(actualActivityList.size()).isEqualTo(2);
		assertThat(actualActivityList.get(0).getRecords().size()).isIn(2, 4);
		assertThat(actualActivityList.get(0).getType()).isEqualTo("Cycling");
	}

	@Test
	public void testDeleteActivity_ShouldDeletedFromDatabase() {
		// Given: an activity
		Activity expectedEveiningRide = entityManager
				.persist(createActivityWithRecords("Eveining Ride", "Cycling", 2));
		// When: delete the activity
		activityRepository.deleteById(expectedEveiningRide.getActivityId());
		// Then: return null from repository
		assertThat(
				entityManager.find(Activity.class, expectedEveiningRide.getActivityId()))
						.isNull();
	}

	@Test
	void testGetActivityByNameType_ShouldReturnActivity() {
		// Given: an activity
		Activity expectedEveiningRide = entityManager
				.persist(createActivityWithRecords("Evening Ride", "Cycling", 2));
		// When: delete the activity
		Optional<Activity> actualActivity = activityRepository
				.findActivityByNameAndTypeAndStartTime("Evening Ride", "Cycling",
						expectedEveiningRide.getStartTime());
		// Then: return null from repository
		assertThat(actualActivity.get().getName()).isEqualTo("Evening Ride");
		assertThat(actualActivity.get().getType()).isEqualTo("Cycling");
	}

}
