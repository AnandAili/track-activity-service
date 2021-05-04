package org.tacx.interview.assignment.trackactivityservice.exception.activity;

public class ActivityNotFoundException extends RuntimeException {

	public ActivityNotFoundException(String activityId) {
		super(activityId);
	}

}
