package org.tacx.interview.assignment.tractactivityservice.exception;

public class ActivityAlreadyExistsException extends RuntimeException{
	public ActivityAlreadyExistsException(Integer activityId) {
		super(String.valueOf(activityId));
	}
}
