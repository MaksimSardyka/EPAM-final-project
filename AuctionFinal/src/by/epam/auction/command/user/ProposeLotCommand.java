package by.epam.auction.command.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.Validator;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.AmountParser;
import by.epam.auction.validator.parser.IdParser;
import by.epam.auction.validator.parser.StringParser;

/**
 * The Class ProposeLotCommand.
 */
public class ProposeLotCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The service. */
	LotService service;

	/**
	 * Instantiates a new propose lot command.
	 *
	 * @param lotService the lot service
	 */
	public ProposeLotCommand(LotService lotService) {
		this.service = lotService;
	}

	/**
	 * Executes ProposeLotCommand command with the
	 * data parsed from the {@link SessionRequestContent} content
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.PROPOSE_LOT);
		ViewPage nextPage = null;
		
			try {
				new Validator().checkCredentials(Role.USER, requestContent);
				Long auctionId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.AUCTION_ID, requestContent);
				User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
				
				StringParser stringParser = new StringParser();
				stringParser.setMaxLenght(ParsingValues.LOT_NAME_MAX_LENGTH);
				String lotName = new ExtractorImpl<String>().extract(stringParser, ParsingValues.LOT_NAME,
						requestContent);
				
				stringParser.setMaxLenght(ParsingValues.LOT_DESCRIPTION_MAX_LENGTH);
				String lotDescription = new ExtractorImpl<String>().extract(stringParser, ParsingValues.LOT_DESCRIPTION,
						requestContent);
				
				BigDecimal startPrice = new ExtractorImpl<BigDecimal>().extract(new AmountParser(), ParsingValues.AMOUNT,
						requestContent);
				Long category = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.CATEGORY_ID,
						requestContent);

				
				List<Part> pictureList = new ArrayList<>(); 
				String path = requestContent.getPath();
				try {
					pictureList.add(requestContent.getRequestPart1());
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}
				try {
					pictureList.add(requestContent.getRequestPart2());
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}
				try {
					pictureList.add(requestContent.getRequestPart3());
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}

				Long lotId = service.proposeLot(user.getId(), auctionId, lotName, lotDescription, startPrice, category, path, pictureList);
				LOG.log(Level.DEBUG, "lot was proposed with Id: " + lotId);
				requestContent.insertRequestAttribute(ParsingValues.LOT_ID, String.valueOf(lotId));
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (AuthenticationException e) {
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
			} catch (WrongInputException e) { 
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				nextPage = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
			}
		return nextPage;
	}
}
