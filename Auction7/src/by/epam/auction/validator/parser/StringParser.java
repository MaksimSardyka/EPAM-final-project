package by.epam.auction.validator.parser;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class StringParser implements Parser<String> {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	private Integer maxLength;

	public void setMaxLenght(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String parse(String string) throws WrongInputException {
		LOG.log(Level.DEBUG, "Parse string: " + string);

		Optional<String> stringOptional = Optional.empty();
		if (string != null && !string.trim().isEmpty()) {
			String value = string.trim();
			if (maxLength != null && value.length() < maxLength && !value.toLowerCase().matches("script")
					&& !value.matches("<") && !value.matches(">")) {
				stringOptional = Optional.ofNullable(value);
			}
		} else {
			LOG.log(Level.ERROR, "Incorrect string: " + string);
		}

		return stringOptional.orElseThrow(() -> new WrongInputException("Non-valid string provided:" + string));
	}
}
