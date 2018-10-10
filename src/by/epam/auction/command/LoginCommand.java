package by.epam.auction.command;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Parser;
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
     * Service to work with DAO.
     */
    UserService service;

    /**
     * Constructor.
     *
     * @param receiver
     *            Service to use to work with DAO.
     */
    public LoginCommand(UserService service) {
        this.service = service;
    }

    /**
     * Operation to perform on login.
     * @throws WrongInputException 
     */
    @Override
    public PageList execute(SessionRequestContent requestContent) throws CommandException {
        LOG.log(Level.DEBUG, "Perform " + CommandType.LOG_IN.name());

    	String login = null;
    	String password = null; 
        try {
            Parser parser = new Parser();
        	login = parser.parseLogin(requestContent.getRequestParameter(ParsingValues.USERNAME)[0]);
        	password = parser.parsePassword(requestContent.getRequestParameter(ParsingValues.PASSWORD)[0]);
        } catch (WrongInputException e) {
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        	throw new CommandException(e);
        }
        
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = service.findUserByLoginPassword(login, password);
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Login service error");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to login you at the moment. Try again later.");
        }
        
        if(optionalUser.isPresent()) {
            requestContent.insertSessionAttribute(ParsingValues.USER, optionalUser.get());
        } else {
        	LOG.log(Level.DEBUG, "Wrong login or password.");
        	requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong login or password.");
        }
        
        PageList page = PageList.NULL_PAGE;
        if (optionalUser.isPresent()) {
        	User user = optionalUser.get();
            if (user.getRole() == Role.USER) {
                LOG.log(Level.DEBUG, "User " + user.getLogin() +" loged-in");
                page = CommandType.AUCTION_SET_PAGE.getCommand().execute(requestContent);
            } else if (user.getRole() == Role.ADMINISTRATOR) {
                LOG.log(Level.DEBUG, "Administrator loged-in");
                page = PageList.ADMIN_PAGE;//TODO Replace with command later?
            }
        } else {
        	page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }

        return page;
    }
}
