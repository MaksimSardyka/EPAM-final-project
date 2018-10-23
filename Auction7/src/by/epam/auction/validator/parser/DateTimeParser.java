package by.epam.auction.validator.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.ParsingValues;
import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class DateTimeParser implements Parser<LocalDateTime>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	public LocalDateTime parse(String datetime) throws WrongInputException{
		LOG.log(Level.DEBUG, "Parse datetime: " + datetime);
		Optional<LocalDateTime> datetimeOptional = Optional.empty();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RegExpAndPatternHolder.DATE_TIME_PATTERN);
		
		if(datetime != null && !datetime.trim().isEmpty()) {
			try {
				LocalDateTime value = LocalDateTime.parse(datetime.trim(), formatter);
				if(value.isAfter(LocalDateTime.now()) && value.isBefore(LocalDateTime.now().plusDays(ParsingValues.DAYS_FROM_NOW))) {
					datetimeOptional = Optional.of(value);
				} else {
					LOG.log(Level.ERROR, "Provided date time is alredy passed");
				}
			} catch (DateTimeParseException e) {
				LOG.log(Level.ERROR, e);
			}
		}
        return datetimeOptional.orElseThrow(() -> new WrongInputException("Non-valid datetime provided:" + datetime));
	}
}
