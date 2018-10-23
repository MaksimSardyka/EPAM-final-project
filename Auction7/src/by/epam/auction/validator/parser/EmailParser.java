package by.epam.auction.validator.parser;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class EmailParser implements Parser<String> {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * Email Reg_Ex.
	 */
	private static final Pattern EMAIL_REG_EX = Pattern.compile(RegExpAndPatternHolder.EMAIL_REG_EX);
	
	/**
	 * Email validation.
	 *
	 * @param email
	 *            Email to validate.
	 * @return {@code true}, if successful
	 * @throws WrongInputException 
	 */
	public String parse(String email) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate email: " + email);
		Optional<String> emailOptional = Optional.empty();

		if (email != null && EMAIL_REG_EX.matcher(email.trim()).matches()) {
			emailOptional = Optional.ofNullable(email.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect email: " + email);
		}
		
		return emailOptional.orElseThrow(() -> new WrongInputException("Non-valid email provided:" + email));
	}
}
