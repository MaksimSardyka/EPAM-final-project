package by.epam.auction.command.administrator;

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
import by.epam.auction.validator.parser.IdParser;

public class ApproveLotCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	LotService service;

	public ApproveLotCommand(LotService service) {
		this.service = service;
	}

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.APPROVE_LOT_COMMAND);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null && user.getRole() == Role.ADMINISTRATOR) {
			try {
				new Validator().checkCredentials(Role.ADMINISTRATOR, requestContent);
				Long lotId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.LOT_ID, requestContent);
				service.approveLot(lotId);
				nextPage = CommandType.VIEW_LOT.getCommand().execute(requestContent);
			} catch (AuthenticationException e) {
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (WrongInputException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
						"Id of lot to approve isn't provided");
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error");
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		}
		return nextPage;
	}
}