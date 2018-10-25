package by.epam.auction.validator.parser;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.domain.AuctionType;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Class AuctionTypeParser.
 */
public class AuctionTypeParser implements Parser<AuctionType>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Parses Auction type.
	 */
	public AuctionType parse(String auctionType) throws WrongInputException {
		LOG.log(Level.DEBUG, "Parse auction type: " + auctionType);
		Optional<AuctionType> typeOptional = Optional.empty();

		if (auctionType != null && !auctionType.trim().isEmpty()) {
			try {
				typeOptional = Optional.ofNullable(AuctionType.valueOf(auctionType.trim().toUpperCase()));
			} catch (IllegalArgumentException e) {
				LOG.log(Level.ERROR, e);
			}
		}
		return typeOptional
				.orElseThrow(() -> new WrongInputException("Non-valid auction type provided:" + auctionType));
	}
}
