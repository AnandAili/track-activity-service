package org.tacx.interview.assignment.tractactivityservice.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.tractactivityservice.api.response.ActivityResponse;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;
import org.tacx.interview.assignment.tractactivityservice.exception.ActivityNotFoundException;
import org.tacx.interview.assignment.tractactivityservice.fileprocessor.CSVDataProcessor;
import org.tacx.interview.assignment.tractactivityservice.service.ActivityService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/activities")
public class ActivityController {

  private ActivityService activityService;
  private CSVDataProcessor csvDataProcessor;

  public ActivityController(ActivityService activityService, CSVDataProcessor csvDataProcessor) {
    this.activityService = activityService;
  }

  @PostMapping(
      value = "/",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @ResponseStatus(code = HttpStatus.CREATED)
  public void createActivity(@RequestParam("file") MultipartFile file) throws Exception {

	  //TODO: check file type
	  //TODO: check file size
	  //TODO: check file extensions

	  Activity activity = csvDataProcessor.getActivityFromCSV(file);
	  activityService.createActivity(activity);

  }



  @GetMapping(
      value = "/{activityId}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ActivityResponse getActivity(@PathVariable("activityId") Integer activityId) {
    Activity activity =
        activityService
            .getActivityById(activityId)
            .orElseThrow(() -> new ActivityNotFoundException(String.valueOf(activityId)));
    return ActivityResponse.creteActivityResponse(activity);
  }

  @GetMapping(value = "/")
  public List<ActivityResponse> getAllActivities() {
    List<Activity> activities = activityService.getAllActivities();
    return activities.stream()
        .map(
            activity -> {
              return ActivityResponse.creteActivityResponse(activity);
            })
        .collect(Collectors.toList());
  }

  @DeleteMapping(value = "/{activityId}")
  public ActivityResponse deleteActivity(@PathVariable("activityId") Integer activityId) {
    ActivityResponse response = null;
    // TODO: checkwhether or not activity is exist
    Optional<Activity> deletedActivity = activityService.deleteActivity(activityId);
    if (deletedActivity.isPresent()) {
      response = ActivityResponse.creteActivityResponse(deletedActivity.get());
    }
    return response;
  }
}
