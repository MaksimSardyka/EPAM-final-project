package by.epam.auction.command.administrator;

import java.time.LocalDateTime;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.parser.AuctionTypeParser;
import by.epam.auction.validator.parser.DateTimeParser;
import by.epam.auction.validator.parser.StringParser;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;

public class CreateAuctionCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	AuctionService service;

	public CreateAuctionCommand(AuctionService service) {
		this.service = service;
	}

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.CREATE_AUCTION);
		ViewPage nextPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null && user.getRole() == Role.ADMINISTRATOR) {
			try {
				new Validator().checkCredentials(Role.ADMINISTRATOR, requestContent);
				LocalDateTime startTime = new ExtractorImpl<LocalDateTime>().extract(new DateTimeParser(),
						ParsingValues.START_TIME, requestContent);
				LocalDateTime finishTime = new ExtractorImpl<LocalDateTime>().extract(new DateTimeParser(),
						ParsingValues.FINISH_TIME, requestContent);
				StringParser descriptionParser = new StringParser();
				descriptionParser.setMaxLenght(ParsingValues.AUCTION_DESCRIPTION_MAX_LENGTH);
				String description = new ExtractorImpl<String>().extract(descriptionParser, ParsingValues.DESCRIPTION,
						requestContent);
				AuctionType auctionType = new ExtractorImpl<AuctionType>().extract(new AuctionTypeParser(),
						ParsingValues.AUCTION_TYPE, requestContent);

				if (startTime.isBefore(finishTime)) {
					boolean isAuctionCreated = service.createAuction(startTime, finishTime, description, auctionType);
					LOG.log(Level.DEBUG, "Auction was created: " + isAuctionCreated);
					requestContent.insertRequestAttribute(ParsingValues.SUCCESS_MESSAGE, "Auction was created");
				} else {
					LOG.log(Level.DEBUG, "Auction start time is after provided finish time");
					requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
							"Auction start time is after provided finish time");
				}
			} catch (AuthenticationException e) {
				LOG.log(Level.DEBUG, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unathorised");
			} catch (WrongInputException e) {
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong input");
				LOG.log(Level.DEBUG, e);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Service error.");
			}
			nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
		}
		return nextPage;
	}
}
