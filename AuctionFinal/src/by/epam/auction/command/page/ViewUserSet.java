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
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.PageNumberParser;

public class ViewUserSet implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	UserService service;

	/**
	 * Constructor.
	 * 
	 * @param lotService
	 *            Service to use to work with users set.
	 */
	public ViewUserSet(UserService service) {
		this.service = service;
	}
	
	/**
	 * Executes ViewUserSet command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_USER_SET);

		ViewPage page = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (null != user && user.getRole() == Role.ADMINISTRATOR) {
			boolean isUserSetFound = false;

			Long pageNumber = 0L;
			try {
				pageNumber = new ExtractorImpl<Long>().extract(new PageNumberParser(), ParsingValues.PAGE_NUMBER, requestContent);
			} catch(WrongInputException e) {
				//FIXME - looks strange
			}

			Optional<Set<User>> userSetOptional = findUserSet(pageNumber);
			if (userSetOptional.isPresent()) {
				isUserSetFound = true;
				addUserSetToResponse(userSetOptional.get(), requestContent);
			}
			if (isUserSetFound) {
				page = ViewPage.VIEW_USER_SET;
			} else {
				page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		}
		return page;
	}

	private Optional<Set<User>> findUserSet(Long pageNumber) {
		Optional<Set<User>> optionalUserSet = Optional.empty();
		try {
			optionalUserSet = service.takeUserSet(ParsingValues.USER_PER_PAGE * pageNumber,
					ParsingValues.USER_PER_PAGE);
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
		}
		return optionalUserSet;
	}

	private void addUserSetToResponse(Set<User> userSet, SessionRequestContent requestContent) {
		if (userSet != null) {
			requestContent.insertRequestAttribute(ParsingValues.USER_SET, userSet);
		} else {
			LOG.log(Level.ERROR, "Wrong user set provided");
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong user set provided.");
		}
	}
}