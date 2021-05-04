package org.tacx.interview.assignment.trackactivityservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Record JPA entity, represents a record data of Activity in the database
 */
@Slf4j
@Entity
@Table(name = "records")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Record implements Comparable<Record> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recordId;

	@EqualsAndHashCode.Include
	private LocalDateTime time = LocalDateTime.now();

	private int distance;

	private int power;

	private int cadence;

	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "activity_id", nullable = false)
	Activity activity;

	@Override
	public int compareTo(Record o) {
		return this.time.compareTo(o.getTime());
	}

}
