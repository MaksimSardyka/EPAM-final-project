package by.epam.auction.command.page;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Lot;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.IdParser;

/**
 * Command to show lot.
 */
public class ViewLot implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Service to work with lot.
	 */
	LotService lotService;

	/**
	 * Constructor.
	 *
	 * @param service
	 *            Service to use to work with lot.
	 */
	public ViewLot(LotService lotService) {
		this.lotService = lotService;
	}

	/**
	 * Operation to perform on show lot.
	 * Executes ViewLot command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_LOT);

		AtomicBoolean isLotFound = new AtomicBoolean(false);

		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);

		ViewPage nextPage = ViewPage.LOGIN_PAGE;
		
		if (user != null) {
			extractLotId(requestContent).ifPresent(lotId -> {
				findLot(lotId, requestContent).ifPresent(lot -> {
					isLotFound.set(true);
					addLotToResponse(lot, requestContent);
				});
			});
			if (isLotFound.get()) {
				nextPage = ViewPage.VIEW_LOT;
			} else {
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}

	private Optional<Long> extractLotId(SessionRequestContent requestContent) {
		LOG.log(Level.TRACE, "Extracting lot id");
		Optional<Long> lotIdOptional = Optional.empty();
		try {
			LOG.log(Level.TRACE, requestContent.getRequestAttributeValue(ParsingValues.LOT_ID));
			lotIdOptional = Optional.ofNullable(new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.LOT_ID, requestContent));
		} catch (WrongInputException e) {
			LOG.log(Level.DEBUG, e);
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id.");
		}
		LOG.log(Level.TRACE, "Lot id extracted:" + lotIdOptional);
		return lotIdOptional;
	}

	private Optional<Lot> findLot(Long lotId, SessionRequestContent requestContent) {
		LOG.log(Level.TRACE, "Requesting lot by id:" + lotId);
		Optional<Lot> lotOptional = Optional.empty();
		if (lotId != null) {
			try {
				lotOptional = lotService.findLotById(lotId);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error. Try again later.");
			}
		}
		LOG.log(Level.TRACE, "Lot taken:" + lotOptional);
		return lotOptional;
	}

	private void addLotToResponse(Lot lot, SessionRequestContent requestContent) {
		LOG.log(Level.TRACE, "Adding lot to response:" + lot);
		if (lot != null) {
			requestContent.insertRequestAttribute(ParsingValues.LOT, lot);
		} else {
			LOG.log(Level.ERROR, "No lot matching id found");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
		}
		LOG.log(Level.TRACE, "Lot added to response:" + lot);
	}
}
