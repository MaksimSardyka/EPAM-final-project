package by.epam.auction.command;

import by.epam.auction.command.administrator.BlockUserCommand;
import by.epam.auction.command.administrator.ShowUserSetCommand;
import by.epam.auction.command.administrator.CreateAuctionCommand;
import by.epam.auction.command.page.LotPage;
import by.epam.auction.command.page.LotSetPage;
import by.epam.auction.command.user.BidCommand;
import by.epam.auction.command.user.ProposeLotCommand;
import by.epam.auction.command.user.RegisterCommand;
import by.epam.auction.command.page.AuctionSetPage;
import by.epam.auction.service.AdministratorService;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.LotService;
import by.epam.auction.service.UserService;

/**
 * List of suitable commands.
 */
public enum CommandType {
    LOG_IN(new LoginCommand(new UserService())),
    LOG_OUT(new LogoutCommand(new UserService())),
	
    REGISTER(new RegisterCommand(new UserService())),
    BID(new BidCommand(new LotService())),
    PROPOSE_LOT(new ProposeLotCommand(new LotService())),
    
    CREATE_AUCTION(new CreateAuctionCommand(new AuctionService())),
    BlOCK_USER(new BlockUserCommand(new AdministratorService())),
    USER_SET_PAGE(new ShowUserSetCommand(new AdministratorService())),
    
    AUCTION_SET_PAGE(new AuctionSetPage(new AuctionService())),
    LOT_SET_PAGE(new LotSetPage(new LotService())),
    LOT_PAGE(new LotPage(new LotService())),
	
    EMPTY_COMMAND(new EmptyCommand());

    /**
     * Command entity.
     */
    private Command command;

    /**
     * Constructor to instantiate command.
     *
     * @param command
     *            Command to instantiate.
     */
    CommandType(final Command command) {
        this.command = command;
    }

    /**
     * Getter on command.
     *
     * @return This command.
     */
    public Command getCommand() {
        return command;
    }
}
