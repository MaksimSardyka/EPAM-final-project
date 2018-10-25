package by.epam.auction.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;

/**
 * Default empty command.
 */
public class EmptyCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

	/**
	 * Executes Empty command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
    @Override
    public ViewPage execute(final SessionRequestContent requestContent) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.EMPTY_COMMAND.name());
        ViewPage page = ViewPage.LOGIN_PAGE;
        
        if (requestContent.getSessionAttributeValue(ParsingValues.USER) != null
                && requestContent.getSessionAttributeValue(ParsingValues.PREVIOUS_PAGE) != null) {
            page = (ViewPage) requestContent.getSessionAttributeValue(ParsingValues.PREVIOUS_PAGE);
        } 
        return page;
    }
}
