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
	 * Get a Activity by It's id
	 * @param activityId
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public Optional<Activity> getActivityById(Integer activityId) {
		return activityRepository.findById(activityId);
	}

	/**
	 * Delete a Activity by It's id.
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
		// TODO: it's Idempotent operation, print it in logger
		return deletedActivity;
	}

	/**
	 * Create a new Activity given complete Activity with It's records.
	 * @param newActivity
	 * @return
	 */
	@Override
	public Optional<Activity> createActivity(Activity newActivity) {
		// TODO: check file duplicate
		var activity = activityRepository.findActivityByNameAndType(newActivity.getName(),
				newActivity.getType());
		activity.ifPresent(a -> {
			// TODO: pass name and type
			throw new ActivityAlreadyExistsException(activity.get().getActivityId());
		});
		return Optional.of(activityRepository.save(newActivity));
	}

	// TODO: write test case for it
	/**
	 * Get all activities
	 * @return
	 */
	@Override
	public List<Activity> getAllActivities() {
		return activityRepository.findAll();
	}

}
