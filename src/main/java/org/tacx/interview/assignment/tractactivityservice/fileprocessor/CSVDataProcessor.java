package org.tacx.interview.assignment.tractactivityservice.fileprocessor;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.tacx.interview.assignment.tractactivityservice.entity.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
public class CSVDataProcessor {




	public Activity getActivityFromCSV(MultipartFile file) {
		Activity activity = null;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));) {
			CSVParser parser = new CSVParserBuilder()
					.withSeparator(',')
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withSkipLines(3)
					.withCSVParser(parser)
					.build();
			Arrays.stream(csvReader.readNext()).forEach(System.out::print);
			//enrichActivityWithRecords(activity, file);
		} catch (IOException | CsvValidationException e) {
			throw new RuntimeException(e);
		}

		return activity;
	}

	private void enrichActivityWithRecords(Activity activity, MultipartFile file) {

	}

	/*public List<String[]> readAll(Reader reader) throws Exception {
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> list = new ArrayList<>();
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list;
	}

	public List<String[]> oneByOne(Reader reader) throws Exception {
		List<String[]> list = new ArrayList<>();
		CSVReader csvReader = new CSVReader(reader);
		String[] line;
		while ((line = csvReader.readNext()) != null) {
			list.add(line);
			Stream.of(line).forEach(System.out::print);
		}
		reader.close();
		csvReader.close();
		return list;
	}*/



}
