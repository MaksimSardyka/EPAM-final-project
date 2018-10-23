package by.epam.auction.command.page;

import by.epam.auction.command.Command;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;

public class ViewAddAuction implements Command{

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
