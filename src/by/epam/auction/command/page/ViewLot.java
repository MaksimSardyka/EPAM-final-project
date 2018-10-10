package by.epam.auction.command.page;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Lot;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.InputParser;
import by.epam.auction.validator.exception.WrongInputException;

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
	LotService service;

	/**
	 * Constructor.
	 *
	 * @param service
	 *            Service to use to work with lot.
	 */
	public ViewLot(LotService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on show lot.
	 * @throws CommandException 
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent){
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_LOT.name());
		Optional<Long> lotIdOptional = Optional.empty();
		
		try {
			lotIdOptional = Optional.of(new InputParser().parseId(requestContent.getRequestParameter(ParsingValues.LOT_ID)[0]));
			LOG.log(Level.TRACE, "Provided lot id is:" + lotIdOptional.get());
		} catch (WrongInputException e) {
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id.");
		}
		
		Optional<Lot> lotOptional = Optional.empty();
		if(lotIdOptional.isPresent()) {
			try {
				lotOptional = new LotService().findLotById(lotIdOptional.get());
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, "Lot service error.");
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error. Try again later.");
			}
		}
			
		if (lotOptional.isPresent()) {
			requestContent.insertRequestAttribute(ParsingValues.LOT, lotOptional.get());
		} else {
			LOG.log(Level.ERROR, "No lot matching id found");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
		}

		ViewPage page = ViewPage.NULL_PAGE;
		if(lotOptional.isPresent()) {
			page = ViewPage.VIEW_LOT;
		} else {
			page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
		}
		return page;
	}
}
