package org.tacx.interview.assignment.tractactivityservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;
import org.tacx.interview.assignment.tractactivityservice.entity.Record;
import org.tacx.interview.assignment.tractactivityservice.repository.ActivityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class TractActivityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TractActivityServiceApplication.class, args);
	}

	@Profile("!test")
	@Bean
	CommandLineRunner dummySetup(ActivityRepository repository) {
		return (args) -> {
			Activity expectedEveiningRide =
					repository.save(createActivityWithRecords("Eveining Ride", "Cycling", 2));
			repository.save(createActivityWithRecords("Morning Ride", "Cycling", 4));
		};
	}

	private Activity createActivityWithRecords(String name, String type, int recordsCount) {
		Activity activity = createActivity(name, type);
		createRecords(activity, recordsCount);
		return activity;
	}

	private List<Record> createRecords(final Activity activity, int count) {
		return IntStream.rangeClosed(1, count)
				.mapToObj(
						i -> {
							var record = activity.createRecord();
							record.setDistance(100 + i);
							record.setTime( LocalDateTime.now().plusMinutes(i));
							record.setPower(80 + i);
							record.setCadence(70 + i);
							return record;
						})
				.collect(Collectors.toList());
	}
	private Activity createActivity(String activityName, String activityType) {
		Activity activity = new Activity();
		activity.setName(activityName);
		activity.setType(activityType);
		activity.setStartTime(LocalDateTime.now());
		return activity;
	}

}
