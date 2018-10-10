package by.epam.auction.command.user;

import java.math.BigDecimal;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class BidCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with DAO.
     */
    LotService service;

    /**
     * Constructor.
     *
     * @param receiver
     *            Service to use to work with DAO.
     */
    public BidCommand(LotService receiver) {
        this.service = receiver;
    }

    @Override
    public PageList execute(SessionRequestContent requestContent) throws CommandException{
        LOG.log(Level.DEBUG, "Perform " + CommandType.BID.name());

        Long lotId;
        BigDecimal amount;
        User user = (User)requestContent.getSessionAttributeValue(ParsingValues.USER);//FIXME - add validation for user + user.id
		try {
	        Parser parser = new Parser();
			lotId = parser.parseId(requestContent.getRequestParameter(ParsingValues.LOT_ID)[0]);
	        amount = parser.parseAmount(requestContent.getRequestParameter(ParsingValues.AMOUNT)[0]);
		} catch (WrongInputException e) {
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong value provided.");
            throw new CommandException(e);
		}

        try {
        	service.placeBid(lotId, amount, user.getId());
        } catch (ServiceException e) {
        	LOG.log(Level.ERROR, "Service error");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to place your bid at the moment.");
        }
            
        return CommandType.LOT_PAGE.getCommand().execute(requestContent);//FIXME - remove redirect as we stay on same page
    }
}
