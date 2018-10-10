package by.epam.auction.command.lot;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.User;
import by.epam.auction.service.LotService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.InputParser;
import by.epam.auction.validator.exception.WrongInputException;

public class BidCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with lot.
     */
    LotService service;

    /**
     * Constructor.
     *
     * @param receiver
     *            Service to use to work with lot.
     */
    public BidCommand(LotService receiver) {
        this.service = receiver;
    }

    @Override
    public ViewPage execute(SessionRequestContent requestContent) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.BID.name());

        Optional<Long> lotId = Optional.empty();
        Optional<BigDecimal> amount = Optional.empty();
        
        //FIXME - add validation for user + user.id
        User user = (User)requestContent.getSessionAttributeValue(ParsingValues.USER);
		try {
	        InputParser parser = new InputParser();
			lotId = Optional.of(parser.parseId(requestContent.getRequestParameter(ParsingValues.LOT_ID)[0]));
	        amount = Optional.of(parser.parseAmount(requestContent.getRequestParameter(ParsingValues.AMOUNT)[0]));
		} catch (WrongInputException e) {
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong value provided.");
		}

		if(lotId.isPresent() 
				&& amount.isPresent()) {//TODO Add user id check here
			try {
				service.placeBid(lotId.get(), amount.get(), user.getId());
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, "Service error");
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to place your bid at the moment.");
			}
		}
		
		return CommandType.VIEW_LOT.getCommand().execute(requestContent);//FIXME - remove redirect as we stay on same page
    }
}
