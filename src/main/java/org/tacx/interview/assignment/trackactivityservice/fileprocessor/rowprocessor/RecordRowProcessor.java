package org.tacx.interview.assignment.trackactivityservice.fileprocessor.rowprocessor;

import com.opencsv.processor.RowProcessor;
import lombok.extern.slf4j.Slf4j;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;
import org.tacx.interview.assignment.trackactivityservice.entity.Record;

import java.time.LocalDateTime;

/**
 * Record Row Processor, more controlled way to process the record data from csv
 */
@Slf4j
public class RecordRowProcessor implements RowProcessor {

	private static final int TIME = 1;

	private static final int DISTANCE = 2;

	private static final int POWER = 3;

	private static final int CADENCE = 4;

	@Override
	public String processColumnItem(String column) {
		if (column == null || !column.isEmpty()) {
			// TODO: check whether data has a http link or any formulas
			return column;
		}
		else {
			return null;
		}
	}

	@Override
	public void processRow(String[] row) {
		for (int i = 1; i < row.length; i++) {
			row[i] = processColumnItem(row[i]);
		}
	}

	public static Record getRecord(String[] csvRecord, Activity activity) {
		Record record = activity.createRecord();
		record.setTime(LocalDateTime.parse(csvRecord[TIME]));
		record.setDistance(Integer.parseInt(csvRecord[DISTANCE]));
		record.setPower(Integer.parseInt(csvRecord[POWER]));
		record.setCadence(Integer.parseInt(csvRecord[CADENCE]));
		return record;
	}

}
