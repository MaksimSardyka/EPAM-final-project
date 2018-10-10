package by.epam.auction.command.user;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * Command to register new user..
 */
public class RegisterCommand implements Command {
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
     * @param service
     *            Service to use to work with DAO.
     */
    public RegisterCommand(UserService service) {
        this.service = service;
    }

    /**
     * Operation to perform on register.
     * @throws CommandException 
     */
    @Override
    public PageList execute(SessionRequestContent requestContent) throws CommandException{
        LOG.log(Level.DEBUG, "Perform " + CommandType.REGISTER.name());

    	String login;
    	String email;
    	String password;
    	String repeatPassword;
        try {
        	Parser parser = new Parser();
        	login = parser.parseLogin(requestContent.getRequestParameter(ParsingValues.USERNAME)[0]);
        	email = parser.parseEmail(requestContent.getRequestParameter(ParsingValues.EMAIL)[0]);
        	password = parser.parsePassword(requestContent.getRequestParameter(ParsingValues.PASSWORD)[0]);
        	repeatPassword = parser.parsePassword(requestContent.getRequestParameter(ParsingValues.REPEAT_PASSWORD)[0]);
        	if(! new Validator().passwordMatchValidate(password, repeatPassword)) {//move to validator class, here just call this method
        		throw new WrongInputException("Provided passwords \"" + password + "\" and \"" + repeatPassword + "\" didn't match");
        	}
        } catch (WrongInputException e) {
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong register data provided.");
            throw new CommandException(e);
        }
        
        boolean userCreated = false;
        try {
        	userCreated = service.createUser(email, login, password);
        	//FIXME somehow insert user to the session here OR redirect to login with Success message inserted here
            LOG.log(Level.DEBUG, "User was created");
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Register service error" + e);
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to register you at the moment. Try again later.");
        }
        
        PageList page = PageList.NULL_PAGE;
        if(userCreated) {
        	page = CommandType.AUCTION_SET_PAGE.getCommand().execute(requestContent);
        } else {
        	page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
        
        return page;
    }
}
