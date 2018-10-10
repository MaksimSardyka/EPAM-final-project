package by.epam.auction.command.user;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.LotService;
public class ProposeLotCommand implements Command {
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
    public ProposeLotCommand(LotService service) {
        this.service = service;
    }

    @Override
    public PageList execute(SessionRequestContent requestContent) throws CommandException {
        LOG.log(Level.DEBUG, "Perform " + CommandType.PROPOSE_LOT.name());
        
        PageList page = PageList.NULL_PAGE;
        return page;
    }

}
