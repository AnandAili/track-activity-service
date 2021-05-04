package org.tacx.interview.assignment.trackactivityservice.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

/**
 * Activity Response Model which summarises the records.
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ActivityResponse {

	private String name;

	private String type;

	private long totalDistance;

	private String totalDuration;

	private double averagePower;

	private double averageCadence;

	/**
	 * Create ActivityResponse out of Activity entity.
	 * @param activity
	 * @return
	 */
	public static ActivityResponse creteActivityResponse(Activity activity) {
		ActivityResponse activityResponse = new ActivityResponse();
		activityResponse.name = activity.getName();
		activityResponse.type = activity.getType();
		activityResponse.totalDistance = activity.totalDistance();
		activityResponse.totalDuration = activity.totalDuration();
		activityResponse.averagePower = activity.averagePower();
		activityResponse.averageCadence = activity.averageCadence();
		return activityResponse;
	}

}
