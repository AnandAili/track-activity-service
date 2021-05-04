package org.tacx.interview.assignment.tractactivityservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;
import org.tacx.interview.assignment.tractactivityservice.exception.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.tractactivityservice.exception.ActivityNotFoundException;
import org.tacx.interview.assignment.tractactivityservice.repository.ActivityRepository;
import org.tacx.interview.assignment.tractactivityservice.service.ActivityService;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultActivityService implements ActivityService {

  private ActivityRepository activityRepository;

  public DefaultActivityService(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }

  /**
   * @param activityId
   * @return
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
  public Optional<Activity> getActivityById(Integer activityId) {
    return activityRepository.findById(activityId);
  }

	/**
	 *
	 * @param activityId
	 * @return
	 */
  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public Optional<Activity> deleteActivity(Integer activityId) {
    Optional<Activity> deletedActivity = activityRepository.findById(activityId);

    deletedActivity.ifPresent(
        activity -> {
          activityRepository.delete(activity);
        });
    //TODO: it's Idempotent operation, print it in logger
    return deletedActivity;
  }

	/**
	 *
	 * @param newActivity
	 * @return
	 */
	@Override
	public Optional<Activity> createActivity(Activity newActivity) {
		var activity = activityRepository.findActivityByNameAndType(newActivity.getName(), newActivity.getType());
		activity.ifPresent( a -> {
			//TODO: pass name and type
			throw new ActivityAlreadyExistsException(activity.get().getActivityId());
		});
		return Optional.of(activityRepository.save(newActivity));
	}
	//TODO: write test case for it
	/**
	 *
	 * @return
	 */
	@Override
	public List<Activity> getAllActivities() {
		return activityRepository.findAll();
	}
}
