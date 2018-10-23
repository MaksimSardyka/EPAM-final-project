package by.epam.auction.command.administrator;

import java.util.Optional;
import java.util.Set;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Lot;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Validator;

public class ViewLotAwaitApproval implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	private LotService service;

	public ViewLotAwaitApproval(LotService service) {
		this.service = service;
	}

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_LOT_AWAIT_APPROVAL);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null && user.getRole() == Role.ADMINISTRATOR) {
			try {
				new Validator().checkCredentials(Role.ADMINISTRATOR, requestContent);
				Optional<Set<Lot>> proposedLotSet = service.findProposedLotSet();
				if (proposedLotSet.isPresent() && !proposedLotSet.get().isEmpty()) {
					requestContent.insertRequestAttribute(ParsingValues.LOT_SET, proposedLotSet.get());
					nextPage = ViewPage.VIEW_LOT_SET;
				} else {
					requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
							"No lots awaiting approval found");
					nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
				}
			} catch (AuthenticationException e) {
				LOG.log(Level.DEBUG, e);
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error.");
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}
}
