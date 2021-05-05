package org.tacx.interview.assignment.trackactivityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Activity JPA Repository to manage life-cycle of an activity & It's records in the
 * database.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	/**
	 * Fetch an activity by name & type
	 * @param name
	 * @param type
	 * @return
	 */
	Optional<Activity> findActivityByNameAndTypeAndStartTime(String name, String type,
			LocalDateTime startTime);

}
