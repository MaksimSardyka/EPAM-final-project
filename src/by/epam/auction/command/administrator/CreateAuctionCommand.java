package by.epam.auction.command.administrator;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.InputParser;
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
	public ViewPage execute(SessionRequestContent requestContent) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.CREATE_AUCTION.name());
        ViewPage page = ViewPage.NULL_PAGE;
        
        Optional<LocalDateTime> startTime = Optional.empty();
        Optional<LocalDateTime> finishTime = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<AuctionType> type = Optional.empty();
        try {
            InputParser parser = new InputParser();
        	startTime  = Optional.of(parser.parseDatetime(requestContent.getRequestParameter(ParsingValues.START_TIME)[0]));
        	finishTime  = Optional.of(parser.parseDatetime(requestContent.getRequestParameter(ParsingValues.FINISH_TIME)[0]));
        	description = Optional.of(parser.parseString(requestContent.getRequestParameter(ParsingValues.DESCRIPTION)[0], DESCRIPTION_MAX_LENGTH));
        	type = Optional.of(parser.parseAuctionType(requestContent.getRequestParameter(ParsingValues.AUCTION_TYPE)[0]));
        } catch (WrongInputException e) {
    		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Wrong auction data provided.");
        }
        
        boolean isAuctionCreated = false; 
        if(startTime.isPresent() 
        		&& finishTime.isPresent()
        		&& !startTime.get().isBefore(finishTime.get())
        		&& description.isPresent()
        		&& type.isPresent()) {
        	try{
        		isAuctionCreated = service.createAuction(startTime.get(), finishTime.get(), description.get(), type.get()); 
        	} catch (ServiceException e) {
        		LOG.log(Level.ERROR, "Auction service error");
        		requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE, "Unable to create auction.");
        	}
        }
        
        if(isAuctionCreated) {
        	page = CommandType.VIEW_AUCTION_SET.getCommand().execute(requestContent);
        } else {
            page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
		return page;
	}
}
