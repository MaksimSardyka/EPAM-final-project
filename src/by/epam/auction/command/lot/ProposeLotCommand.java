package by.epam.auction.command.lot;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.LotService;
public class ProposeLotCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with lot entities.
     */
    LotService service;

    /**
     * Constructor.
     *
     * @param receiver
     *            Service to use to work with lot.
     */
    public ProposeLotCommand(LotService service) {
        this.service = service;
    }

    @Override
    public ViewPage execute(SessionRequestContent requestContent){
        LOG.log(Level.DEBUG, "Perform " + CommandType.PROPOSE_LOT.name());
        
        ViewPage page = ViewPage.NULL_PAGE;
        return page;
    }

}
