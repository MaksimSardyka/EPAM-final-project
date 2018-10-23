package by.epam.auction.validator.parser;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class AmountParser implements Parser<BigDecimal>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	private static final Pattern MONEY_AMOUNT_REGEX = Pattern.compile(RegExpAndPatternHolder.MONEY_AMOUNT_REG_EX);
	
	public BigDecimal parse(String amount) throws WrongInputException {
		LOG.log(Level.DEBUG, "Validate amount: " + amount);
		Optional<BigDecimal> optionalAmount = Optional.empty();

		if (amount != null && !amount.trim().isEmpty() && MONEY_AMOUNT_REGEX.matcher(amount).matches()) {
			try {
				BigDecimal value = new BigDecimal(amount.trim());
				optionalAmount = Optional.ofNullable(value);
			} catch (NumberFormatException e) {
				LOG.log(Level.ERROR, e);
			}
		}
		
		return optionalAmount.orElseThrow(() -> new WrongInputException("Non-valid amount provided:" + amount));
	}
}
