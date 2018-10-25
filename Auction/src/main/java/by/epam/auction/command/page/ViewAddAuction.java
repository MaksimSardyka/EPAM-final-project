package by.epam.auction.command.page;

import by.epam.auction.command.Command;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;

/**
 * The Class ViewAddAuction.
 */
public class ViewAddAuction implements Command {

	/**
	 * Executes ViewAddAuction command with the data parsed from the
	 * {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		ViewPage viewPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);

		if (user != null && user.getRole() == Role.ADMINISTRATOR) {
			viewPage = ViewPage.ADD_AUCTION;
		}
		return viewPage;
	}

}
