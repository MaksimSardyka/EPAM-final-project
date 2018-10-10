package by.epam.auction.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.page.PageList;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.UserService;

import static by.epam.auction.constant.ParsingValues.USER;

/**
 * Command to logout.
 */
public class LogoutCommand implements Command {
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
    public LogoutCommand(UserService receiver) {
        this.service = receiver;
    }

    /**
     * Operation to perform on logout.
     */
    @Override
    public PageList execute(SessionRequestContent content) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.LOG_OUT.name());
        
        //TODO session invalidate here???
        content.removeSessionAttribute(USER);
        return PageList.LOGIN_PAGE;
    }
}
