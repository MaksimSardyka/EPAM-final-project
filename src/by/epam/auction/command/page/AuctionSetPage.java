package by.epam.auction.command.page;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Auction;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;

public class AuctionSetPage implements Command{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with DAO.
     */
    AuctionService service;

    /**
     * Constructor.
     *
     * @param service
     *            Service to use to work with DAO.
     */
    public AuctionSetPage(AuctionService service) {
        this.service = service;
    }

    /**
     * Operation to perform on show lot.
     * @throws CommandException 
     */
    @Override
    public PageList execute(SessionRequestContent requestContent) throws CommandException {
        LOG.log(Level.DEBUG, "Perform " + CommandType.AUCTION_SET_PAGE.name());
        PageList page = PageList.NULL_PAGE;
        
        try {
            Optional<Set<Auction>> optionalAuctionSet = service.getAll();
            if (optionalAuctionSet.isPresent()) {
                LOG.log(Level.DEBUG, "Found " + optionalAuctionSet.get().size() + " auctions");
                requestContent.insertRequestAttribute(ParsingValues.AUCTION_SET, optionalAuctionSet.get());
                page = PageList.AUCTION_PAGE;
            } else {
                LOG.log(Level.ERROR, "No auctions found");
            }
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Find auction service error");
            page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
        
        return page;
    }
}
