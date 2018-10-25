package by.epam.auction.command.page;

import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.IdParser;

/**
 * The Class ViewProposeLot.
 */
public class ViewProposeLot implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The service. */
	LotService service;

	/**
	 * Instantiates a new view propose lot.
	 *
	 * @param service the service
	 */
	public ViewProposeLot(LotService service) {
		this.service = service;
	}


	/**
	 * Executes ViewProposeLot command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_PROPOSE_LOT);
		ViewPage nextPage = null;	
		try {
			new Validator().checkCredentials(Role.USER, requestContent);
			Long auctionId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.AUCTION_ID, requestContent);
			Optional<Map<Long, String>> categoryMap = Optional.ofNullable(service.takeCategoryMap());
			if (categoryMap.isPresent()) { 
				requestContent.insertRequestAttribute(ParsingValues.PROPOSED_LOT_SET, categoryMap.get().entrySet());
				requestContent.insertRequestAttribute(ParsingValues.AUCTION_ID, auctionId);
				nextPage = ViewPage.PROPOSE_LOT;
			} else {
				LOG.log(Level.ERROR, "");
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Can't accept your lot at the moment. Try again later.");
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		} catch (AuthenticationException e) {
			LOG.log(Level.ERROR, e);
			nextPage = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
		} catch (WrongInputException e) {
			LOG.log(Level.ERROR, "Wrong \"" + ParsingValues.AUCTION_ID + "\" parameter provided");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong auction id provided.");
			nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error");
			nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
		}
		return nextPage;
	}
}
