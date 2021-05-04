package org.tacx.interview.assignment.trackactivityservice.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.tacx.interview.assignment.trackactivityservice.TestActivities;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.storage.impl.FileSystemStorageService;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for StorageService
 */
class FileSystemStorageServiceTest {

	private StorageProperties properties = new StorageProperties();

	private FileSystemStorageService service;

	@BeforeEach
	public void init() {
		// properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
		properties.setLocation("testfiles");
		service = new FileSystemStorageService(properties);
		// service.init();
	}

	@Test
	@EnabledOnOs({ OS.LINUX })
	public void saveAbsolutePathInFilenamePermitted() {
		// Unix file systems (e.g. ext4) allows backslash '\' in file names.
		Activity dummyActivity = TestActivities.createActivityWithRecords("Morning Ride",
				"Cycling", 2);
		String fileName = "test.csv";
		service.store(
				new MockMultipartFile(fileName, fileName, MediaType.TEXT_PLAIN_VALUE,
						"activty_def,name,type,start_time,".getBytes()),
				dummyActivity);
		assertTrue(Files.exists(
				Paths.get(properties.getLocation()).resolve(Paths.get(fileName))));
	}

}
