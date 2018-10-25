package by.epam.auction.command.common;

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
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.EmailParser;
import by.epam.auction.validator.parser.LoginParser;
import by.epam.auction.validator.parser.PasswordParser;

/**
 * Command to register new user.
 */

public class UpdateUserCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Service to work with user entities.
	 */
	UserService service;

	/**
	 * Constructor.
	 * 
	 * @param service
	 *            Service to use to work with user entities.
	 */
	public UpdateUserCommand(UserService service) {
		this.service = service;
	}

	/**
	 * Executes UpdateUserCommand with the data parsed from the
	 * {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.UPDATE_USER.name());
		ViewPage nextPage = ViewPage.LOGIN_PAGE;

		User oldUser = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (oldUser != null) {
			try {
				String usernameOptional = new ExtractorImpl<String>().extract(new LoginParser(), ParsingValues.USERNAME,
						requestContent);
				String emailOptional = new ExtractorImpl<String>().extract(new EmailParser(), ParsingValues.EMAIL,
						requestContent);
				String passwordOptional = new ExtractorImpl<String>().extract(new PasswordParser(),
						ParsingValues.PASSWORD, requestContent);
				String repeatPasswordOptional = new ExtractorImpl<String>().extract(new PasswordParser(),
						ParsingValues.REPEAT_PASSWORD, requestContent);

				if (usernameOptional.equals(oldUser.getLogin())) {
					if (new Validator().passwordMatchValidate(passwordOptional, repeatPasswordOptional)) {
						User user = service.updateUserInfo(oldUser.getId(), emailOptional, passwordOptional);
						if (null != user) {
							requestContent.insertRequestAttribute(ParsingValues.SUCCESS_MESSAGE,
									"User sucessfully updated.");
							requestContent.insertSessionAttribute(ParsingValues.USER, user);
							LOG.log(Level.DEBUG, "User " + usernameOptional + " was updated");
							if (user.getRole() == Role.USER) {
								LOG.log(Level.DEBUG, "User " + user.getLogin() + " was updated");
							} else if (user.getRole() == Role.ADMINISTRATOR) {
								LOG.log(Level.DEBUG, "Administrator was updated");
							}
						}
					} else {
						requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
								"Provided passwords didn't match each other");
						LOG.log(Level.ERROR, "Provided passwords didn't match each other");
					}
				}
			} catch (WrongInputException e) {
				LOG.log(Level.ERROR, e);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
						"Unable to update you at the moment. Try again later.");
			}
			nextPage = CommandType.VIEW_USER_DATA.getCommand().execute(requestContent);
		}
		return nextPage;
	}
}
