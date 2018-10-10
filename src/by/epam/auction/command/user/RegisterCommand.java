package by.epam.auction.command.user;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.InputParser;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;

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
     * @throws CommandException 
     */
    @Override
    public ViewPage execute(SessionRequestContent requestContent) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.REGISTER.name());

    	Optional<String> login = Optional.empty();
    	Optional<String> email = Optional.empty();
    	Optional<String> password = Optional.empty();
    	Optional<String> repeatPassword = Optional.empty();
        try {
        	InputParser parser = new InputParser();
        	login = Optional.of(parser.parseLogin(requestContent.getRequestParameter(ParsingValues.USERNAME)[0]));
        	email = Optional.of(parser.parseEmail(requestContent.getRequestParameter(ParsingValues.EMAIL)[0]));
        	password = Optional.of(parser.parsePassword(requestContent.getRequestParameter(ParsingValues.PASSWORD)[0]));
        	repeatPassword = Optional.of(parser.parsePassword(requestContent.getRequestParameter(ParsingValues.REPEAT_PASSWORD)[0]));
        } catch (WrongInputException e) {
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong register data provided.");
        }
        
        boolean isPasswordPairMatch = new Validator().passwordMatchValidate(password.get(), repeatPassword.get());
        if(!isPasswordPairMatch) {
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Provided passwords \"" + password.get() + "\" and \"" + repeatPassword.get() + "\" didn't match");
        }
        
        boolean userCreated = false;
        if(login.isPresent()
        		&& email.isPresent()
        		&& password.isPresent()
        		&& repeatPassword.isPresent()
        		&& isPasswordPairMatch) {
        	try {
        		userCreated = service.createUser(email.get(), login.get(), password.get());
        		//FIXME somehow insert user to the session here OR redirect to login with Success message inserted here
        		LOG.log(Level.DEBUG, "User was created");
        	} catch (ServiceException e) {
        		LOG.log(Level.ERROR, "Register service error" + e);
        		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to register you at the moment. Try again later.");
        	}
        }
        
        ViewPage page = ViewPage.NULL_PAGE;
        if(userCreated) {
        	page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
        } else {
        	page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
        
        return page;
    }
}
