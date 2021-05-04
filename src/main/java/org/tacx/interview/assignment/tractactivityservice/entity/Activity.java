package org.tacx.interview.assignment.tractactivityservice.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Entity
@Table(name = "activities")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Activity implements Comparable<Activity> {

	public Activity(){}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityId;

	@NotNull
	//@Column(unique = true)
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

	public Record createRecord() {
		var record =  new Record();
		this.getRecords().add(record);
		record.setActivity(this);
		return record;
	}

	public long totalDistance() {
		return records.stream()
				.mapToInt(Record::getDistance)
				.sum();
	}

	public String totalDuration() {
		StringBuilder totalDuration = new StringBuilder();
		LocalDateTime startDate = records.stream().map(Record::getTime).sorted().findFirst().get();
		LocalDateTime lastRecordedDate = records.stream().map(Record::getTime).sorted((a,b) -> b.compareTo(a)).findFirst().get();

		LocalDateTime tempDateTime = LocalDateTime.from( startDate );
		long minutes = tempDateTime.until( lastRecordedDate, ChronoUnit.MINUTES );
		tempDateTime = tempDateTime.plusMinutes( minutes );
		totalDuration.append(minutes).append(":");

		long seconds = tempDateTime.until( lastRecordedDate, ChronoUnit.SECONDS );
		totalDuration.append(seconds);

		return totalDuration.toString();
	}

	public double averagePower() {
		return records.stream()
				.mapToInt(Record::getPower)
				.summaryStatistics()
				.getAverage();
	}

	public double averageCadence() {
		return records.stream()
				.mapToInt(Record::getCadence)
				.summaryStatistics()
				.getAverage();
	}
}
