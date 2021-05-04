package org.tacx.interview.assignment.trackactivityservice.fileprocessor;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.fileprocessor.rowprocessor.ActivityRowProcessor;
import org.tacx.interview.assignment.trackactivityservice.fileprocessor.rowprocessor.RecordRowProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Data Processor to map CSV format to a JPA entity
 */
@Slf4j
@Component
public class CSVToEntityProcessor {

	/**
	 * Read Activity & records data from CSV and map it to corresponding JPA entities.
	 * @param file
	 * @return
	 */
	public Activity getActivityFromCSV(MultipartFile file) {
		Activity activity = null;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream()));) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',')
					.withIgnoreLeadingWhiteSpace(true).build();
			/*
			 * Read Activity data and map it to Activity entity
			 */
			CSVReader csvReaderActivity = new CSVReaderBuilder(reader).withSkipLines(1)
					.withCSVParser(parser).withRowProcessor(new ActivityRowProcessor())
					.build();
			Activity csvActivity = ActivityRowProcessor
					.getActivity(csvReaderActivity.readNext());

			/*
			 * Read Record data and map it to Record entity
			 */
			CSVReader csvReaderRecord = new CSVReaderBuilder(reader).withSkipLines(3)
					.withCSVParser(parser).withRowProcessor(new RecordRowProcessor())
					.build();
			csvReaderRecord.readAll().stream()
					.forEach(row -> RecordRowProcessor.getRecord(row, csvActivity));

			// complete activity with It's records
			activity = csvActivity;
		}
		catch (IOException | CsvException e) {
			throw new RuntimeException(e);
		}

		return activity;
	}

}
