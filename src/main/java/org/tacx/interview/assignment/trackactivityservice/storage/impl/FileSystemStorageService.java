package org.tacx.interview.assignment.trackactivityservice.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.exception.storage.StorageException;
import org.tacx.interview.assignment.trackactivityservice.storage.StorageProperties;
import org.tacx.interview.assignment.trackactivityservice.storage.StorageService;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.tacx.interview.assignment.trackactivityservice.fileprocessor.Files.getDateAsString;

/**
 * File System Implementation for {@link StorageService}
 */
@Slf4j
@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
		init();
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException(
						"Failed to store empty file " + file.getOriginalFilename());
			}

			Files.copy(file.getInputStream(), this.rootLocation
					.resolve(file.getOriginalFilename() + getDateAsString()));
		}
		catch (IOException e) {
			throw new StorageException(
					"Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		}
		catch (FileAlreadyExistsException e) {
			log.info("Folder {} is already exists", rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

}
