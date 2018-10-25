package by.epam.auction.command.page;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.User;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;

/**
 * The Class ViewUserData.
 */
public class ViewUserData implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The service. */
	UserService service;

	/**
	 * Constructor.
	 *
	 * @param service the service
	 */
	public ViewUserData(UserService service) {
		this.service = service;
	}

	/**
	 * Executes ViewUserData command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		ViewPage viewPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (null != user && null != user.getId()) {
			try {
				Optional<User> optionalUser = service.findUserById(user.getId());
				if (optionalUser.isPresent()) {
					requestContent.insertSessionAttribute(ParsingValues.USER, optionalUser.get());
					LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_USER_DATA);
					viewPage = ViewPage.VIEW_USER_DATA;
				}
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error. Try again later.");
			}
		}
		return viewPage;
	}
}
