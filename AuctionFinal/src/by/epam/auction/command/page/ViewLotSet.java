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
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.IdParser;
import by.epam.auction.validator.parser.PageNumberParser;

public class ViewLotSet implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

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
	 * Executes ViewLotAwaitApproval command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_LOT_SET);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;

		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (null != user) {
			if (!user.isBlocked()) {
				try {
					Long auctionId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.AUCTION_ID,
							requestContent);
					Long pageNumber = new ExtractorImpl<Long>().extract(new PageNumberParser(),
							ParsingValues.PAGE_NUMBER, requestContent);
					LOG.log(Level.DEBUG, "Requested lot set for auction " + auctionId + " on page � " + pageNumber);
					Optional<Set<Lot>> lotSetOptional = findLotSet(auctionId, pageNumber);
					if (lotSetOptional.isPresent()) {
						addLotSetToResponse(lotSetOptional.get(), requestContent);
						requestContent.insertRequestAttribute(ParsingValues.AUCTION_ID, auctionId);
						nextPage = ViewPage.VIEW_LOT_SET;
					} else {
						nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
					}
				} catch (WrongInputException e) {
					LOG.log(Level.ERROR, e);
				}
			} else {
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}

	private Optional<Set<Lot>> findLotSet(Long auctionId, Long frontendPageId) {
		LOG.log(Level.DEBUG, "");
		Optional<Set<Lot>> optionalLotSet = Optional.empty();
		try {
			optionalLotSet = service.takeLotSet(auctionId, ParsingValues.LOT_PER_PAGE * frontendPageId,
					ParsingValues.LOT_PER_PAGE);
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
		}
		LOG.log(Level.DEBUG, "Found "+ (optionalLotSet.isPresent()?optionalLotSet.get().size():0) + " lot entities");
		return optionalLotSet;
	}

	private void addLotSetToResponse(Set<Lot> lotSet, SessionRequestContent requestContent) {
		if (lotSet != null) {
			requestContent.insertRequestAttribute(ParsingValues.LOT_SET, lotSet);
		} else {
			LOG.log(Level.ERROR, "Wrong lot set provided");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong lot set provided.");
		}
	}
}
