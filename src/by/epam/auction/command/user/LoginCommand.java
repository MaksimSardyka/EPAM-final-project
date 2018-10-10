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
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.InputParser;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * Command to login.
 */
public class LoginCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with user.
     */
    UserService service;

    /**
     * Constructor.
     *
     * @param receiver
     *            Service to use to work with user.
     */
    public LoginCommand(UserService service) {
        this.service = service;
    }

    /**
     * Operation to perform on login.
     * @throws WrongInputException 
     */
    @Override
    public ViewPage execute(SessionRequestContent requestContent){
        LOG.log(Level.DEBUG, "Perform " + CommandType.LOG_IN.name());

    	Optional<String> login = Optional.empty();
    	Optional<String> password = Optional.empty(); 
        try {
            InputParser parser = new InputParser();
        	login = Optional.of(parser.parseLogin(requestContent.getRequestParameter(ParsingValues.USERNAME)[0]));
        	password = Optional.of(parser.parsePassword(requestContent.getRequestParameter(ParsingValues.PASSWORD)[0]));
        } catch (WrongInputException e) {
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        }
        
        Optional<User> optionalUser = Optional.empty();
        if(login.isPresent() && password.isPresent()) {
        	try {
        		optionalUser = service.findUserByLoginPassword(login.get(), password.get());
        	} catch (ServiceException e) {
        		LOG.log(Level.ERROR, "Login service error");
        		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Login service error. Try again later.");
        	}
        }
        
        if(optionalUser.isPresent()) {
            requestContent.insertSessionAttribute(ParsingValues.USER, optionalUser.get());
        } else {
        	LOG.log(Level.DEBUG, "Wrong login or password.");
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        }
        
        ViewPage page = ViewPage.NULL_PAGE;
        if (optionalUser.isPresent()) {
        	User user = optionalUser.get();
            if (user.getRole() == Role.USER) {
                LOG.log(Level.DEBUG, "User " + user.getLogin() +" loged-in");
                page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
            } else if (user.getRole() == Role.ADMINISTRATOR) {
                LOG.log(Level.DEBUG, "Administrator loged-in");
                page = ViewPage.ADMIN_PAGE;//TODO Replace with command later?
            }
        } else {
        	page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }

        return page;
    }
}
