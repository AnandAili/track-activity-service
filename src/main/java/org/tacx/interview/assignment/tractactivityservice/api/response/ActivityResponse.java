package org.tacx.interview.assignment.tractactivityservice.api.response;

import lombok.Getter;
import lombok.Setter;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;


@Getter @Setter
public class ActivityResponse {
	public ActivityResponse(){}
	private String name;
	private String type;
	private long totalDistance;
	private String totalDuration;
	private double averagePower;
	private double averageCadence;

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
