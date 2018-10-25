package by.epam.auction.command.user;

import java.math.BigDecimal;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.AmountParser;
import by.epam.auction.validator.parser.IdParser;

public class BidCommand implements Command {
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
	 * @param receiver
	 *            Service to use to work with lot.
	 */
	public BidCommand(LotService receiver) {
		this.service = receiver;
	}
	
	/**
	 * Executes BidCommand command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.BID);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null) {
			try {
				new Validator().checkCredentials(Role.USER, requestContent);

				Long lotId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.LOT_ID, requestContent);
				BigDecimal amount = new ExtractorImpl<BigDecimal>().extract(new AmountParser(), ParsingValues.AMOUNT,
						requestContent);

				user = placeBid(lotId, amount, user.getId(), requestContent);
				if (user != null) {
					requestContent.insertSessionAttribute(ParsingValues.USER, user);
				}
				nextPage = CommandType.VIEW_LOT.getCommand().execute(requestContent);
			} catch (AuthenticationException e) {
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (WrongInputException e) {
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.VIEW_LOT.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}

	private User placeBid(Long lotId, BigDecimal amount, Long userId, SessionRequestContent requestContent) {
		User newBidderState = null;
		try {
			newBidderState = service.placeBid(lotId, amount, userId);
			LOG.log(Level.DEBUG, "Bid " + ((newBidderState != null)?"was":"wasn't") + " placed by user");
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
					"Unable to place your bid at the moment.");
		}
		return newBidderState;
	}
}
