package org.tacx.interview.assignment.trackactivityservice.storage;

import org.springframework.web.multipart.MultipartFile;

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
	 */
	void store(MultipartFile file);

}
