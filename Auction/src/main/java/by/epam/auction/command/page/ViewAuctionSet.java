package by.epam.auction.command.page;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Auction;
import by.epam.auction.domain.User;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;

public class ViewAuctionSet implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Service to work with auction.
	 */
	AuctionService service;

	/**
	 * Constructor.
	 *
	 * @param service
	 *            Service to use to work with auction.
	 */
	public ViewAuctionSet(AuctionService service) {
		this.service = service;
	}

	/**
	 * Executes ViewAuctionSet command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_AUCTION_SET);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;

		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null) {
			Optional<Set<Auction>> optionalAuctionSet = findAuctionSet();

			if (optionalAuctionSet.isPresent()) {
				addAuctionSetToResponse(optionalAuctionSet.get(), requestContent);
			} else {
				LOG.log(Level.ERROR, "No auctions found");
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "No auctions yet.");
			}
			nextPage = ViewPage.VIEW_AUCTION_SET;
		}
		return nextPage;
	}

	private Optional<Set<Auction>> findAuctionSet() {
		Optional<Set<Auction>> optionalAuctionSet = Optional.empty();
		try {
			optionalAuctionSet = service.getAllAuction();
		} catch (ServiceException e) {
			LOG.log(Level.ERROR, e);
		}
		return optionalAuctionSet;
	}

	private void addAuctionSetToResponse(Set<Auction> auctionSet, SessionRequestContent requestContent) {
		requestContent.insertRequestAttribute(ParsingValues.AUCTION_SET, auctionSet);
		LOG.log(Level.DEBUG, "Found " + auctionSet.size() + " auctions");
	}
}
