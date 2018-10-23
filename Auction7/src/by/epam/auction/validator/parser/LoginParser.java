package by.epam.auction.validator.parser;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class LoginParser implements Parser<String>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * Login Rex_Ex.
	 */
	private static final Pattern LOGIN_REG_EX = Pattern.compile(RegExpAndPatternHolder.LOGIN_REG_EX);
	
	/**
	 * Login validation.
	 *
	 * @param login
	 *            Login to validate.
	 * @return {@code true}, if matches the Reg_Ex.
	 * @throws WrongInputException 
	 */
	public String parse(String login) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate login: " + login);
		Optional<String> loginOptional = Optional.empty();

		if (login != null && LOGIN_REG_EX.matcher(login.trim()).matches()) {
			loginOptional = Optional.ofNullable(login.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect login: " + login);
		}
		return loginOptional.orElseThrow(() -> new WrongInputException("Non-valid login provided:" + login));
	}
}
