package by.epam.auction.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.User;

public class ChangeLanguageCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.CHANGE_LANGUAGE);
		ViewPage nextPage = null;
		
		Language locale = Language.EN_US;
			try {
				locale = Language.valueOf(requestContent.getRequestParameter(ParsingValues.LANGUAGE)[0].toUpperCase());
				LOG.log(Level.DEBUG, "Locale defined: " + locale);				
			} catch (IllegalArgumentException | NullPointerException e) {
				LOG.log(Level.ERROR, e);
			}
		requestContent.insertSessionAttribute(ParsingValues.LOCALE, locale.getLocale());
		
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		
		if(user != null) {
			nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
		} else {
			nextPage = ViewPage.LOGIN_PAGE;
		}
		
		return nextPage;
	}

}
