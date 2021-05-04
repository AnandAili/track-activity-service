package org.tacx.interview.assignment.trackactivityservice.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.api.response.ActivityResponse;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityNotFoundException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.EmptyFileException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.MultipleFileExtensionException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.WrongFileExtensionException;
import org.tacx.interview.assignment.trackactivityservice.exception.storage.StorageException;
import org.tacx.interview.assignment.trackactivityservice.fileprocessor.CSVToEntityProcessor;
import org.tacx.interview.assignment.trackactivityservice.fileprocessor.Files;
import org.tacx.interview.assignment.trackactivityservice.service.ActivityService;
import org.tacx.interview.assignment.trackactivityservice.storage.impl.FileSystemStorageService;
import org.tacx.interview.assignment.trackactivityservice.storage.StorageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Activity Service API endpoints to create, get & delete activity details.
 */
@Slf4j
@RestController
@RequestMapping("/activities")
public class ActivityController {

	private ActivityService activityService;

	private CSVToEntityProcessor csvToEntityProcessor;

	private StorageService storageService;

	public ActivityController(ActivityService activityService,
			CSVToEntityProcessor csvToEntityProcessor,
			FileSystemStorageService storageService) {
		this.activityService = activityService;
		this.csvToEntityProcessor = csvToEntityProcessor;
		this.storageService = storageService;
	}

	/**
	 * POST API to create a new Activity and It's records given in the uploaded file.
	 * @param file
	 * @throws {@link EmptyFileException} - uploaded file is blank or file attachment is
	 * missing {@link StorageException} - If unable to store uploaded file
	 * {@link MultipleFileExtensionException} - If uploaded file has more than one
	 * extensions. for example: filename.exe.csv {@link WrongFileExtensionException} - If
	 * uploaded file is not a csv format.
	 */
	@PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createActivity(@RequestParam("file") MultipartFile file)
			throws Exception {
		// Basic validation for file like size, extension, etc
		Files.validate(file);
		Activity activity = csvToEntityProcessor.getActivityFromCSV(file);
		activityService.createActivity(activity);
		storageService.store(file);

	}

	/**
	 * Get API to fetch a Activity summary details.
	 * @param activityId
	 * @return
	 * @throws {@link ActivityNotFoundException} - If the activityId is not found
	 */
	@GetMapping(value = "/{activityId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ActivityResponse getActivity(@PathVariable("activityId") Integer activityId) {
		Activity activity = activityService.getActivityById(activityId).orElseThrow(
				() -> new ActivityNotFoundException(String.valueOf(activityId)));
		return ActivityResponse.creteActivityResponse(activity);
	}

	/**
	 * Get API to fetch all Activities details
	 * @return
	 */
	@GetMapping(value = "/")
	public List<ActivityResponse> getAllActivities() {
		List<Activity> activities = activityService.getAllActivities();
		return activities.stream().map(activity -> {
			return ActivityResponse.creteActivityResponse(activity);
		}).collect(Collectors.toList());
	}

	/**
	 * Delete API to remove a Activity.
	 * @param activityId
	 * @return
	 */
	@DeleteMapping(value = "/{activityId}")
	public ActivityResponse deleteActivity(
			@PathVariable("activityId") Integer activityId) {
		ActivityResponse response = null;
		// TODO: checkwhether or not activity is exist
		Optional<Activity> deletedActivity = activityService.deleteActivity(activityId);
		if (deletedActivity.isPresent()) {
			response = ActivityResponse.creteActivityResponse(deletedActivity.get());
		}
		return response;
	}

}
