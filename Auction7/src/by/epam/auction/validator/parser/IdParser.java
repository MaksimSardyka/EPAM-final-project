package by.epam.auction.validator.parser;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class IdParser implements Parser<Long>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	public Long parse(String id) throws WrongInputException {
		LOG.log(Level.DEBUG, "Parse id: " + id);
		Optional<Long> optionalId = Optional.empty();

		if (id != null && !id.trim().isEmpty()) {
			try {
				Long idValue = Long.valueOf(id.trim());
				if (idValue > 0L) {
					optionalId = Optional.of(idValue);
				} else {
					LOG.log(Level.ERROR, "Negative or 0 id");
				}
			} catch (NumberFormatException e) {
				LOG.log(Level.ERROR, e);
			}
		}
		return optionalId.orElseThrow(() -> new WrongInputException("Non-valid id provided:" + id));
	}
}
