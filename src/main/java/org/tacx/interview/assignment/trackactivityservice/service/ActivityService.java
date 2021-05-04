package org.tacx.interview.assignment.trackactivityservice.service;

import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

import java.util.List;
import java.util.Optional;

/**
 * Activity Service API
 */
public interface ActivityService {

	/**
	 * Get a Activity by It's id
	 * @param activityId
	 * @return
	 */
	Optional<Activity> getActivityById(Integer activityId);

	/**
	 * Delete a Activity by It's id.
	 * @param activityId
	 * @return
	 */
	Optional<Activity> deleteActivity(Integer activityId);

	/**
	 * Create a new Activity given complete Activity with It's records.
	 * @param newActivity
	 * @return
	 */
	Optional<Activity> createActivity(Activity newActivity);

	/**
	 * Get all activities
	 * @return
	 */
	List<Activity> getAllActivities();

}
