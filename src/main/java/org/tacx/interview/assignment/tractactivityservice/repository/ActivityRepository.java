package org.tacx.interview.assignment.tractactivityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	/**
	 *
	 * @param name
	 * @param type
	 * @return
	 */
	Optional<Activity> findActivityByNameAndType(String name, String type);
}
