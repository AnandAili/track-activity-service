package org.tacx.interview.assignment.trackactivityservice;

import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.entity.Record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Activity Util class to create Activity & records for testing
 */
public class TestActivities {

	public static Activity createActivity(String activityName, String activityType) {
		Activity activity = new Activity();
		activity.setName(activityName);
		activity.setType(activityType);
		activity.setStartTime(LocalDateTime.now());
		return activity;
	}

	public static Activity createActivityWithRecords(String name, String type,
			int recordsCount) {
		Activity activity = createActivity(name, type);
		createRecords(activity, recordsCount);
		return activity;
	}

	public static List<Record> createRecords(final Activity activity, int count) {
		return IntStream.rangeClosed(1, count).mapToObj(i -> {
			var record = activity.createRecord();
			record.setDistance(100 + i);
			record.setTime(LocalDateTime.now().plusMinutes(i * i));
			record.setPower(80 + i);
			record.setCadence(70 + i);
			return record;
		}).collect(Collectors.toList());
	}

}
