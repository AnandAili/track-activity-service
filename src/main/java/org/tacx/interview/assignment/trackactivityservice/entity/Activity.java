package org.tacx.interview.assignment.trackactivityservice.entity;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Activity JPA entity represents an activity data in the database.
 */
@Slf4j
@Entity
@Table(name = "activities")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Activity implements Comparable<Activity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityId;

	@NotNull
	@EqualsAndHashCode.Include
	private String name;

	@NotNull
	@EqualsAndHashCode.Include
	private String type;

	@NotNull
	private LocalDateTime startTime;

	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Record> records = new TreeSet<>();

	@Override
	public int compareTo(Activity o) {
		return this.startTime.compareTo(o.startTime);
	}

	/**
	 * Create a record within this Activity
	 * @return
	 */
	public Record createRecord() {
		var record = new Record();
		this.getRecords().add(record);
		record.setActivity(this);
		return record;
	}

	/**
	 * Calculate the total distance
	 * @return
	 */
	public long totalDistance() {
		return records.stream().mapToInt(Record::getDistance).sum();
	}

	/**
	 * Calculate the total duration
	 * @return
	 */
	public String totalDuration() {
		StringBuilder totalDuration = new StringBuilder();
		LocalDateTime startDate = records.stream().map(Record::getTime).sorted()
				.findFirst().get();
		LocalDateTime lastRecordedDate = records.stream().map(Record::getTime)
				.sorted((a, b) -> b.compareTo(a)).findFirst().get();

		LocalDateTime tempDateTime = LocalDateTime.from(startDate);
		long minutes = tempDateTime.until(lastRecordedDate, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);
		totalDuration.append(minutes).append(" min:");

		long seconds = tempDateTime.until(lastRecordedDate, ChronoUnit.SECONDS);
		totalDuration.append(seconds).append(" sec");

		return totalDuration.toString();
	}

	/**
	 * Calculate the average of power
	 * @return
	 */
	public double averagePower() {
		return records.stream().mapToInt(Record::getPower).summaryStatistics()
				.getAverage();
	}

	/**
	 * Calculate the average of Cadence
	 * @return
	 */
	public double averageCadence() {
		return records.stream().mapToInt(Record::getCadence).summaryStatistics()
				.getAverage();
	}

}
