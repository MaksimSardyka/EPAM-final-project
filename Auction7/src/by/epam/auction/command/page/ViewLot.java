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
	 * 
	 * @throws CommandException
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
				nextPage = CommandType.VIEW_LOT_SET.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}

	private Optional<Long> extractLotId(SessionRequestContent requestContent) {
		Optional<Long> lotIdOptional = Optional.empty();
		try {
			lotIdOptional = Optional
					.of(new IdParser().parse(requestContent.getRequestParameter(ParsingValues.LOT_ID)[0]));
		} catch (WrongInputException e) {
			LOG.log(Level.DEBUG, e);
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id.");
		}
		return lotIdOptional;
	}

	private Optional<Lot> findLot(Long lotId, SessionRequestContent requestContent) {
		Optional<Lot> lotOptional = Optional.empty();
		if (lotId != null) {
			try {
				lotOptional = lotService.findLotById(lotId);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error. Try again later.");
			}
		}
		return lotOptional;
	}

	private void addLotToResponse(Lot lot, SessionRequestContent requestContent) {
		if (lot != null) {
			requestContent.insertRequestAttribute(ParsingValues.LOT, lot);
		} else {
			LOG.log(Level.ERROR, "No lot matching id found");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
		}
	}
}
