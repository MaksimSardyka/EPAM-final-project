package by.epam.auction.command.page;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Lot;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * Command to show lot.
 */
public class LotPage implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Service to work with DAO.
	 */
	LotService service;

	/**
	 * Constructor.
	 *
	 * @param service
	 *            Service to use to work with DAO.
	 */
	public LotPage(LotService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on show lot.
	 * @throws CommandException 
	 */
	@Override
	public PageList execute(SessionRequestContent requestContent) throws CommandException {
		LOG.log(Level.DEBUG, "Perform " + CommandType.LOT_PAGE.name());

		Long lotId;
		try {
			Parser parser = new Parser();
			lotId = parser.parseId(requestContent.getRequestParameter(ParsingValues.LOT_ID)[0]);
		} catch (WrongInputException e) {
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id.");
			throw new CommandException(e);
		}
		
		Optional<Lot> lot = Optional.empty();
		try {
			lot = new LotService().findLotById(lotId);
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, "Show lot service error");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error. Try again later.");
		}
		
		if (lot.isPresent()) {
			requestContent.insertRequestAttribute(ParsingValues.LOT, lot.get());
		} else {
			LOG.log(Level.ERROR, "No lot matching id found");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
		}

		PageList page = PageList.NULL_PAGE;
		if(lot.isPresent()) {
			page = PageList.LOT_PAGE;
		} else {
			page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
		}
		return page;
	}
}
