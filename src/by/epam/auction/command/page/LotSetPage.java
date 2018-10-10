package by.epam.auction.command.page;

import java.util.Optional;
import java.util.Set;

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

public class LotSetPage implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Number of entities to show on each page.
	 */
	private static final int LOT_PER_PAGE = 20;

	LotService service;

	/**
	 * Constructor.
	 * 
	 * @param lotService
	 *            Service to use to work with DAO.
	 */
	public LotSetPage(LotService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on main page.
	 * @throws CommandException 
	 */
	@Override
	public PageList execute(SessionRequestContent requestContent) throws CommandException {
		LOG.log(Level.DEBUG, "Perform " + CommandType.LOT_SET_PAGE.name());

		Parser parser = new Parser();
		Long lotId = null;
		try {
			lotId = parser.parseId(requestContent.getRequestParameter(ParsingValues.AUCTION_ID)[0]);
		} catch (WrongInputException e1) {
            LOG.log(Level.DEBUG, "Non-valid lot id provided");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
        }
		int pageId = 0;//FIXME - add parameters for page number(PAGINATION)
		
		Optional<Set<Lot>> optionalLotSet = Optional.empty();
		try {
			optionalLotSet = service.takeLotSet(lotId, LOT_PER_PAGE * pageId, LOT_PER_PAGE);//FIXME move this parameter to constant class
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, "Find lot service error");
		}
		
		if (optionalLotSet.isPresent()) {
			LOG.log(Level.DEBUG, "Found " + optionalLotSet.get().size() + " lots");
			requestContent.insertRequestAttribute(ParsingValues.LOT_SET, optionalLotSet.get());
		} else {
			LOG.log(Level.ERROR, "No lots found");
		}

		PageList page;
		if(optionalLotSet.isPresent()) {
			page = PageList.LOT_SET_PAGE;
		} else {
			page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);	
		}
		return page;
	}
}
