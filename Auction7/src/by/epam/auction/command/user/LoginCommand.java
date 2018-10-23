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
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.LoginParser;
import by.epam.auction.validator.parser.PasswordParser;

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
        LOG.log(Level.DEBUG, "Perform " + CommandType.LOG_IN);
    	ViewPage page = ViewPage.LOGIN_PAGE;

        try {
        	String username = new ExtractorImpl<String>().extract(
        		new LoginParser(),
        		ParsingValues.USERNAME,
        		requestContent);
        	String password = new ExtractorImpl<String>().extract(
        		new PasswordParser(),
        		ParsingValues.PASSWORD,
        		requestContent);
    	
        	Optional<User> optionalUser = service.findUserByLoginPassword(username, password);
        
        	if(optionalUser.isPresent()) {
        		requestContent.insertSessionAttribute(ParsingValues.USER, optionalUser.get());
        		User user = optionalUser.get();
        		if (user.getRole() == Role.USER) {
        			LOG.log(Level.DEBUG, "User loged-in: " + user);
        		} else if (user.getRole() == Role.ADMINISTRATOR) {
        			LOG.log(Level.DEBUG, "Administrator loged-in:" + user);
        		}
        		page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
        	} else {
        		LOG.log(Level.DEBUG, "User matching credentials not found.");
        		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        	}
        	
        } catch (WrongInputException e) {
        	LOG.log(Level.ERROR, e);
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        } catch (ServiceException e) {
    		LOG.log(Level.ERROR, e);
    		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Login service error. Try again later.");
    	}
        return page;
    }
}
