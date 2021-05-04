package org.tacx.interview.assignment.trackactivityservice.fileprocessor.rowprocessor;

import com.opencsv.processor.RowProcessor;
import lombok.extern.slf4j.Slf4j;
import org.tacx.interview.assignment.trackactivityservice.entity.Activity;

import java.time.LocalDateTime;

/**
 * Activity Row Processor, more controlled way to process the activity data from csv
 */
@Slf4j
public class ActivityRowProcessor implements RowProcessor {

	private static final int NAME = 1;

	private static final int TYPE = 2;

	private static final int START_TIME = 3;

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

	/**
	 * Map a row to Activity Entity
	 * @param csvActivity
	 * @return
	 */
	public static Activity getActivity(String[] csvActivity) {
		Activity activity = new Activity();
		activity.setName(csvActivity[NAME]);
		activity.setType(csvActivity[TYPE]);
		activity.setStartTime(LocalDateTime.parse(csvActivity[START_TIME]));
		return activity;
	}

}
