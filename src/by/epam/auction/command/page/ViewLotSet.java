package by.epam.auction.command.page;

import java.util.Optional;
import java.util.Set;

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

public class ViewLotSet implements Command {
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
	 *            Service to use to work with lot set.
	 */
	public ViewLotSet(LotService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on main page.
	 * @throws CommandException 
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent){
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_LOT_SET.name());

		Optional<Long> lotId = Optional.empty();
		int pageId = 0;//TODO - add parameter for page number(PAGINATION)
		try {
			lotId = Optional.of(new InputParser().parseId(requestContent.getRequestParameter(ParsingValues.AUCTION_ID)[0]));
		} catch (WrongInputException e1) {
            LOG.log(Level.DEBUG, "Non-valid lot id provided");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot id provided.");
        }
		
		Optional<Set<Lot>> optionalLotSet = Optional.empty();
		if(lotId.isPresent()) {
			try {
				optionalLotSet = service.takeLotSet(lotId.get(), LOT_PER_PAGE * pageId, LOT_PER_PAGE);//FIXME move this parameter to constant class
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, "Find lot service error");
			}
		}
		
		if (optionalLotSet.isPresent()) {
			LOG.log(Level.DEBUG, "Found " + optionalLotSet.get().size() + " lots");
			requestContent.insertRequestAttribute(ParsingValues.LOT_SET, optionalLotSet.get());
		} else {
			LOG.log(Level.ERROR, "No lots found");
		}

		ViewPage page;
		if(optionalLotSet.isPresent()) {
			page = ViewPage.VIEW_LOT_SET;
		} else {
			page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);	
		}
		return page;
	}
}
