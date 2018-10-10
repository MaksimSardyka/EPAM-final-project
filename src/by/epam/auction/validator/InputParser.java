package by.epam.auction.validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.domain.AuctionType;
import by.epam.auction.validator.exception.WrongInputException;

public class InputParser {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * The Constant PASSWORD_REG_EX.
	 */
	private static final Pattern PASSWORD_REG_EX = Pattern
			.compile("(?=^.{6,40}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
	
	private static final Pattern MONEY_AMOUNT_REGEX = Pattern.compile("^\\d+\\.\\d{2}$");
	
	/**
	 * Login Rex_Ex.
	 */
	private static final Pattern LOGIN_REG_EX = Pattern.compile("^[A-Za-z0-9_]{1,40}$");
	
	/**
	 * Email Reg_Ex.
	 */
	private static final Pattern EMAIL_REG_EX = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");

	public String parseString(String string, int maxLength) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate string: " + string);
		Optional<String> stringOptional = Optional.empty();

		if (string != null && string.trim().length()!=0 && string.trim().length()<maxLength) {
			stringOptional = Optional.ofNullable(string.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect string: " + string);
		}

		return stringOptional.orElseThrow(() -> new WrongInputException("Non-valid string provided:" + string));
	}
	
	public  AuctionType parseAuctionType(String auctionType) throws WrongInputException{
		LOG.log(Level.DEBUG, "Validate auction type: " + auctionType);
		Optional<AuctionType> typeOptional = Optional.empty();
		
		if(auctionType != null && auctionType.trim().length()!=0) {
			try {
				typeOptional = Optional.ofNullable(AuctionType.valueOf(auctionType.trim().toUpperCase()));
			} catch (IllegalArgumentException  e) {
				LOG.log(Level.ERROR, e);
			}
		}
        return typeOptional.orElseThrow(() -> new WrongInputException("Non-valid auction type provided:" + auctionType));
		
	}
	
	public LocalDateTime parseDatetime(String datetime) throws WrongInputException{
		LOG.log(Level.DEBUG, "Validate datetime: " + datetime);
		Optional<LocalDateTime> datetimeOptional = Optional.empty();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		
		if(datetime != null && datetime.trim().length()!=0) {
			try {
				datetimeOptional = Optional.ofNullable(LocalDateTime.parse(datetime.trim(), formatter));
			} catch (DateTimeParseException e) {
				LOG.log(Level.ERROR, e);
			}
		}
        return datetimeOptional.orElseThrow(() -> new WrongInputException("Non-valid datetime provided:" + datetime));
	}
	
	/**
	 * Parse validation.
	 *
	 * @param password
	 *            Password to validate.
	 * @return {@code true}, if matches the Reg_Ex.
	 * @throws WrongInputException 
	 */
	public String parsePassword(String password) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate password: " + password);
		Optional<String> loginOptional = Optional.empty();

		if (password != null && PASSWORD_REG_EX.matcher(password.trim()).matches()) {
			loginOptional = Optional.ofNullable(password.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect password: " + password);
		}
		return loginOptional.orElseThrow(() -> new WrongInputException("Non-valid passwword provided:" + password));
	}
	
	/**
	 * Login validation.
	 *
	 * @param login
	 *            Login to validate.
	 * @return {@code true}, if matches the Reg_Ex.
	 * @throws WrongInputException 
	 */
	public String parseLogin(String login) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate login: " + login);
		Optional<String> loginOptional = Optional.empty();

		if (login != null && LOGIN_REG_EX.matcher(login.trim()).matches()) {
			loginOptional = Optional.ofNullable(login.trim());
		} else {
			LOG.log(Level.ERROR, "Incorrect login: " + login);
		}
		return loginOptional.orElseThrow(() -> new WrongInputException("Non-valid login provided:" + login));
	}
	

	public BigDecimal parseAmount(String amount) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate amount: " + amount);
		Optional<BigDecimal> optionalAmount = Optional.empty();

		if (amount != null && amount.trim().length() != 0 && MONEY_AMOUNT_REGEX.matcher(amount).matches()) {
			try {
				BigDecimal value = new BigDecimal(amount.trim());
				optionalAmount = Optional.ofNullable(value);
			} catch (NumberFormatException e) {
				LOG.log(Level.ERROR, e);
			}
		}
		
		return optionalAmount.orElseThrow(() -> new WrongInputException("Non-valid amount provided:" + amount));
	}
	
	public Long parseId(String id) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate id: " + id);
		Optional<Long> optionalId = Optional.empty();

		if (id != null && id.trim().length() != 0) {
			try {
				Long value = Long.valueOf(id.trim());
				if (value.compareTo(0L) == 1) {
					optionalId = Optional.ofNullable(value);
				} else {
					LOG.log(Level.ERROR, "Negative or 0 id");
				}
			} catch (NumberFormatException e) {
				LOG.log(Level.ERROR, e);
			}
		}
		return optionalId.orElseThrow(() -> new WrongInputException("Non-valid id provided:" + id));
	}
	
	/**
	 * Email validation.
	 *
	 * @param email
	 *            Email to validate.
	 * @return {@code true}, if successful
	 * @throws WrongInputException 
	 */
	public String parseEmail(String email) throws WrongInputException {
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
