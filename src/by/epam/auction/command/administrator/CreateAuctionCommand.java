package by.epam.auction.command.administrator;
import java.time.LocalDateTime;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class CreateAuctionCommand implements Command{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    private static final int DESCRIPTION_MAX_LENGTH = 200;//FIXME - probably, should be moved to class of constants OR resource file
	
	AuctionService service;
	
	public CreateAuctionCommand(AuctionService service){
		this.service = service;
	}

	@Override
	public PageList execute(SessionRequestContent requestContent) throws CommandException {
        LOG.log(Level.DEBUG, "Perform " + CommandType.CREATE_AUCTION.name());
        PageList page = PageList.NULL_PAGE;
        
        LocalDateTime startTime = null;
        LocalDateTime finishTime = null;
        String description = null;
        AuctionType type = null;
        Parser parser = new Parser();
        try {
        	startTime  = parser.parseDatetime(requestContent.getRequestParameter(ParsingValues.START_TIME)[0]);
        	finishTime  = parser.parseDatetime(requestContent.getRequestParameter(ParsingValues.FINISH_TIME)[0]);
        	description = parser.parseString(requestContent.getRequestParameter(ParsingValues.DESCRIPTION)[0], DESCRIPTION_MAX_LENGTH);
        	type = parser.parseAuctionType(requestContent.getRequestParameter(ParsingValues.AUCTION_TYPE)[0]);
        	if(!finishTime.isAfter(startTime)) {
        		throw new WrongInputException(startTime + " isn't after " + finishTime);
        	}
        } catch (WrongInputException e) {
    		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong auction data provided.");
        	throw new CommandException(e);
        }
        
        boolean lotCreated = false;
        try{
        	lotCreated = service.createAuction(startTime, finishTime, description, type); 
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Create auction service error");
            requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to create auction.");
        }
        
        if(lotCreated) {
        	page = CommandType.AUCTION_SET_PAGE.getCommand().execute(requestContent);
        } else {
            page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
		return page;
	}

}
