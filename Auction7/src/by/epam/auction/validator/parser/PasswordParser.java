package by.epam.auction.validator.parser;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class PasswordParser implements Parser<String>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * The Constant PASSWORD_REG_EX.
	 */
	private static final Pattern PASSWORD_REG_EX = Pattern.compile(RegExpAndPatternHolder.PASSWORD_REG_EX);
	
	/**
	 * Parse validation.
	 *
	 * @param password
	 *            Password to validate.
	 * @return {@code true}, if matches the Reg_Ex.
	 * @throws WrongInputException 
	 */
	public String parse(String password) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate password.");
		Optional<String> loginOptional = Optional.empty();

		if (password != null && PASSWORD_REG_EX.matcher(password.trim()).matches()) {
			loginOptional = Optional.of(password.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect password: " + password);
		}
		return loginOptional.orElseThrow(() -> new WrongInputException("Non-valid passwword provided."));
	}
}
