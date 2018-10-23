package by.epam.auction.command.lot;

import java.io.IOException;
import java.math.BigDecimal;

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

public class ProposeLotCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	LotService service;

	public ProposeLotCommand(LotService lotService) {
		this.service = lotService;
	}

	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.PROPOSE_LOT);
		ViewPage page = ViewPage.LOGIN_PAGE;

		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (user != null) {
			try {
				new Validator().checkCredentials(Role.USER, requestContent);
				StringParser stringParser = new StringParser();
				stringParser.setMaxLenght(ParsingValues.LOT_NAME_MAX_LENGTH);
				String lotName = new ExtractorImpl<String>().extract(stringParser, ParsingValues.LOT_NAME,
						requestContent);
				stringParser.setMaxLenght(ParsingValues.LOT_DESCRIPTION_MAX_LENGTH);
				String lotDescription = new ExtractorImpl<String>().extract(stringParser, ParsingValues.LOT_DESCRIPTION,
						requestContent);
				BigDecimal amount = new ExtractorImpl<BigDecimal>().extract(new AmountParser(), ParsingValues.AMOUNT,
						requestContent);
				Long auctionId = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.AUCTION_ID,
						requestContent);
				Long optionalCategory = new ExtractorImpl<Long>().extract(new IdParser(), ParsingValues.CATEGORY_ID,
						requestContent);

				Part file1 = null;
				Part file2 = null;
				Part file3 = null;
				String path = requestContent.getPath();
				try {
					file1 = requestContent.getRequestPart1();
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}
				try {
					file2 = requestContent.getRequestPart2();
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}
				try {
					file3 = requestContent.getRequestPart3();
				} catch (IOException | ServletException e) {
					LOG.log(Level.ERROR, e);
				}

				Long lotId = service.proposeLot(user.getId(), auctionId, lotName, lotDescription, amount,
						optionalCategory, path, file1, file2, file3);
				LOG.log(Level.DEBUG, "lot was proposed");
				requestContent.insertRequestAttribute(ParsingValues.LOT_ID, lotId);
				page = ViewPage.VIEW_LOT;
			} catch (AuthenticationException | WrongInputException | ServiceException e) {
				page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
				LOG.log(Level.ERROR, e);
			}
		}
		return page;
	}
}
