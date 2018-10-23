package by.epam.auction.command.user;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
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
public class RegisterCommand implements Command {
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
	public RegisterCommand(UserService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on register.
	 * 
	 * @throws CommandException
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.REGISTER);
		ViewPage viewPage = ViewPage.LOGIN_PAGE;

		try {
    	String usernameOptional = new ExtractorImpl<String>().extract(
        		new LoginParser(),
        		ParsingValues.USERNAME,
        		requestContent);
		String emailOptional  = new ExtractorImpl<String>().extract(
        		new EmailParser(),
        		ParsingValues.EMAIL,
        		requestContent);
    	String passwordOptional = new ExtractorImpl<String>().extract(
        		new PasswordParser(),
        		ParsingValues.PASSWORD,
        		requestContent);
		String repeatPasswordOptional = new ExtractorImpl<String>().extract(
        		new PasswordParser(),
        		ParsingValues.REPEAT_PASSWORD,
        		requestContent);

		if (checkPasswordMatch(passwordOptional, repeatPasswordOptional, requestContent)) {
				User user = service.createUser(emailOptional, usernameOptional, passwordOptional);
				if (null != user) {
					requestContent.insertRequestAttribute(ParsingValues.SUCCESS_MESSAGE, "User sucessfully registered.");
					requestContent.insertSessionAttribute(ParsingValues.USER, user);
					LOG.log(Level.DEBUG, "User " + usernameOptional + " was created");
					viewPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
				} 
			}
		} catch (WrongInputException e) {
			LOG.log(Level.ERROR, e);
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
			requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
					"Unable to register you at the moment. Try again later.");
		}
		return viewPage;
	}	
	
	private boolean checkPasswordMatch(String password, String repeatPassword, SessionRequestContent requestContent) {
			boolean isPasswordPairMatch = new Validator().passwordMatchValidate(password, repeatPassword);
			if (!isPasswordPairMatch) {
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
						"Provided passwords didn't match each other");
				LOG.log(Level.ERROR, "Provided passwords didn't match each other");
			}
			return isPasswordPairMatch;
	}
}
