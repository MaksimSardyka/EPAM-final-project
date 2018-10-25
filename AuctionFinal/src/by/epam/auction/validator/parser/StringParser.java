package by.epam.auction.validator.parser;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Class StringParser.
 */
public class StringParser implements Parser<String> {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The max length. */
	private Integer maxLength;

	/**
	 * Sets the max lenght.
	 *
	 * @param maxLength the new max lenght
	 */
	public void setMaxLenght(Integer maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Parses string data.
	 */
	public String parse(String string) throws WrongInputException {
		LOG.log(Level.DEBUG, "Parse string: " + string);

		Optional<String> stringOptional = Optional.empty();
		if (string != null && !string.trim().isEmpty()) {
			String value = string.trim();
			if (maxLength != null && value.length() <= maxLength) {
				stringOptional = Optional.ofNullable(value);
			}
		} else {
			LOG.log(Level.ERROR, "Incorrect string: " + string);
		}

		return stringOptional.orElseThrow(() -> new WrongInputException("Non-valid string provided:" + string));
	}
}
