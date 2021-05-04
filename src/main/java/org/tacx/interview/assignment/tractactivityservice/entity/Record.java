package org.tacx.interview.assignment.tractactivityservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 */
@Entity
@Table(name = "records")
@Getter
@Setter
@EqualsAndHashCode
public class Record implements Comparable<Record>{

	public Record(){}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recordId;

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
