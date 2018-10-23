package by.epam.auction.command.page;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;

public class ViewProposeLot implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	LotService service;

	public ViewProposeLot(LotService service) {
		this.service = service;
	}

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		ViewPage nextPage = ViewPage.LOGIN_PAGE;

		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null && user.getRole() == Role.USER) {
				try {
					Optional<Map<Long, String>> categoryMap = Optional.ofNullable(service.takeCategoryMap());
					if (categoryMap.isPresent()) {
						requestContent.insertRequestAttribute(ParsingValues.PROPOSED_LOT_SET,
								categoryMap.get().entrySet());
						nextPage = ViewPage.PROPOSE_LOT;
					}
				} catch (ServiceException e) {
					LOG.log(Level.ERROR, e);
				}
		}
		return nextPage;
	}
}
