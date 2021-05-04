package org.tacx.interview.assignment.tractactivityservice.service;

import org.tacx.interview.assignment.tractactivityservice.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
	/**
	 *
	 * @param activityId
	 * @return
	 */
	Optional<Activity> getActivityById(Integer activityId);

	/**
	 *
	 * @param activityId
	 * @return
	 */
	Optional<Activity> deleteActivity(Integer activityId);

	/**
	 *
	 * @param newActivity
	 * @return
	 */
	Optional<Activity> createActivity(Activity newActivity);

	/**
	 *
	 * @return
	 */
	List<Activity> getAllActivities();

}
