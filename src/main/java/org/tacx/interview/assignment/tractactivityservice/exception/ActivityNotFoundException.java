package org.tacx.interview.assignment.tractactivityservice.exception;

public class ActivityNotFoundException extends RuntimeException{
	public ActivityNotFoundException(String activityId) {
		super(activityId);
	}
}
