package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public class DateTimeHelper {
	private Logger log = Logger.getLogger(DateTimeHelper.class.getName());

	/** returns the formatted date/time string. **/
	public String getCurrentDateTime(String format) {

		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String currentDateTime = dateFormat.format(date);
		log.info("currentDateTime is : " + currentDateTime);
		return currentDateTime;
	}

	/** returns current local time string **/
	public String getCurrentTime(String format) {
		LocalTime time = LocalTime.now();
		String currentTime = time.format(DateTimeFormatter.ofPattern(format));
		log.info("currentTime is : " + currentTime);
		return currentTime;
	}

	/** returns user defined formatted date string **/
	public String formatDate(String originalDateFormat, String targetDateFormat, String originalDate) {
		DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
		try {
			String formattedDate = targetFormat.format(originalFormat.parse(originalDate));
			log.info("required formattted date is : " + formattedDate);
			return formattedDate;
		} catch (ParseException e) {
			e.printStackTrace();
			log.info("Exception found : " + e);
		}
		return null;
	}

}
