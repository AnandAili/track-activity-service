package org.tacx.interview.assignment.trackactivityservice.storage;

import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

/**
 * Storage Service API
 */
public interface StorageService {

	/**
	 * create a folder
	 */
	void init();

	/**
	 * Copy file to already configured folder
	 * @param file
	 * @param activity
	 */
	void store(MultipartFile file, Activity activity);

}
