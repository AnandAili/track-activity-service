package org.tacx.interview.assignment.trackactivityservice.exception.activity;

public class ActivityAlreadyExistsException extends RuntimeException {

	public ActivityAlreadyExistsException(Integer activityId) {
		super(String.valueOf(activityId));
	}

}
