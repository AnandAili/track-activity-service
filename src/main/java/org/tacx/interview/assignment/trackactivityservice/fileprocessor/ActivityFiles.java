package org.tacx.interview.assignment.trackactivityservice.fileprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.exception.file.EmptyFileException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.MultipleFileExtensionException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.WrongFileExtensionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility Class for Basic validations for uploaded file
 */
@Slf4j
public class ActivityFiles {

	public static final String SPACE = " ";

	public static final String BLANK = "";

	public static final String HYPHEN = "-";

	public static final String CSV = ".csv";

	public static void validate(MultipartFile file) {
		isEmptyFile(file);
		isCSVExtension(file);
	}

	private static void isCSVExtension(MultipartFile file) {
		String[] extensions = file.getOriginalFilename().split("\\.");
		if (extensions.length != 2) {
			throw new MultipleFileExtensionException(
					"Mulltiple file extension are found in uploaded file");
		}
		if (!(extensions[1].equals("csv"))) {
			throw new WrongFileExtensionException(
					"Uploaded file extension is not allowed" + extensions[1]);
		}

	}

	private static void isEmptyFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new EmptyFileException("Uploaded empty file or forgot to attach file");
		}
	}

	public static String getDateAsString() {
		DateTimeFormatter oldPattern = DateTimeFormatter
				.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		LocalDateTime datetime = LocalDateTime.parse(new Date().toInstant().toString(),
				oldPattern);
		return datetime.format(newPattern);
	}

	public static String getNewFileName(MultipartFile file, Activity activity) {
		String originalFilenae = file.getOriginalFilename().split("\\.")[0];
		StringBuilder newFilename = new StringBuilder(originalFilenae);
		newFilename.append(HYPHEN).append(activity.getName().replace(SPACE, BLANK))
				.append(HYPHEN).append(activity.getType().replace(SPACE, BLANK))
				.append(HYPHEN).append(getDateAsString()).append(CSV);
		return newFilename.toString();
	}

}
