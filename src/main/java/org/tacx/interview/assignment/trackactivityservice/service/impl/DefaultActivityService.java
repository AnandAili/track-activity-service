package org.tacx.interview.assignment.trackactivityservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.trackactivityservice.repository.ActivityRepository;
import org.tacx.interview.assignment.trackactivityservice.service.ActivityService;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link ActivityService}
 */
@Slf4j
@Service
public class DefaultActivityService implements ActivityService {

	private ActivityRepository activityRepository;

	public DefaultActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	/**
	 * Get an Activity by It's id
	 * @param activityId
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public Optional<Activity> getActivityById(Integer activityId) {
		Optional<Activity> activity = activityRepository.findById(activityId);
		log.info("Activity {} is found");
		return activity;
	}

	/**
	 * Delete an Activity by It's id.
	 * @param activityId
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public Optional<Activity> deleteActivity(Integer activityId) {
		Optional<Activity> deletedActivity = activityRepository.findById(activityId);

		deletedActivity.ifPresent(activity -> {
			activityRepository.delete(activity);
		});
		log.info("Activity {} is deleted successfully", activityId);
		return deletedActivity;
	}

	/**
	 * Create a new Activity given complete Activity with It's records.
	 * @param newActivity
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public Optional<Activity> createActivity(Activity newActivity) {
		// TODO: check file duplicate
		var activity = activityRepository.findActivityByNameAndTypeAndStartTime(
				newActivity.getName(), newActivity.getType(), newActivity.getStartTime());
		boolean isActivityFound = activity.isPresent();
		if (activity.isPresent()) {
			// Delete all records and insert new once
			log.info("Activity {} is already exist", activity.get().getActivityId());
			activityRepository.delete(activity.get());
			newActivity = activityRepository.save(newActivity);
			log.info("Inserted new records to activity {} successfully",
					newActivity.getActivityId());
		}
		else {
			newActivity = activityRepository.save(newActivity);
			log.info("Created a new Activity {} successfully",
					newActivity.getActivityId());
		}
		return Optional.of(newActivity);
	}

	// TODO: write test case for it
	/**
	 * Get all activities
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public List<Activity> getAllActivities() {
		return activityRepository.findAll();
	}

}
