package by.epam.auction.command;

import by.epam.auction.command.administrator.BlockUserCommand;
import by.epam.auction.command.administrator.UnblockUserCommand;
import by.epam.auction.command.common.AddFundsCommand;
import by.epam.auction.command.common.LoginCommand;
import by.epam.auction.command.common.LogoutCommand;
import by.epam.auction.command.common.UpdateUserCommand;
import by.epam.auction.command.common.WithdrawFundsCommand;
import by.epam.auction.command.language.ChangeLanguageCommand;
import by.epam.auction.command.administrator.CreateAuctionCommand;
import by.epam.auction.command.administrator.ApproveLotCommand;
import by.epam.auction.command.administrator.UnApproveLotCommand;
import by.epam.auction.command.page.ViewLot;
import by.epam.auction.command.page.ViewLotAwaitApproval;
import by.epam.auction.command.page.ViewLotSet;
import by.epam.auction.command.page.ViewProposeLot;
import by.epam.auction.command.user.ProposeLotCommand;
import by.epam.auction.command.user.RegisterCommand;
import by.epam.auction.command.user.BidCommand;
import by.epam.auction.command.page.ViewUserData;
import by.epam.auction.command.page.ViewUserSet;
import by.epam.auction.command.page.ViewAddAuction;
import by.epam.auction.command.page.ViewAuctionSet;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.LotService;
import by.epam.auction.service.UserService;

/**
 * List of suitable commands.
 */
public enum CommandType {
    
    /** The log in. */
    LOG_IN(new LoginCommand(new UserService())),
    
    /** The log out. */
    LOG_OUT(new LogoutCommand(new UserService())),
    
    /** The register. */
    REGISTER(new RegisterCommand(new UserService())),
    
    /** The update user. */
    UPDATE_USER(new UpdateUserCommand(new UserService())),
    
    /** The add funds. */
    ADD_FUNDS(new AddFundsCommand(new UserService())),
    
    /** The withdraw funds. */
    WITHDRAW_FUNDS(new WithdrawFundsCommand(new UserService())),
    
    /** The bid. */
    BID(new BidCommand(new LotService())),
    
    /** The view propose lot. */
    VIEW_PROPOSE_LOT(new ViewProposeLot(new LotService())),
    
    /** The propose lot. */
    PROPOSE_LOT(new ProposeLotCommand(new LotService())),
    
    /** The create auction. */
    CREATE_AUCTION(new CreateAuctionCommand(new AuctionService())),
    
    /** The block user. */
    BLOCK_USER(new BlockUserCommand(new UserService())),
    
    /** The unblock user. */
    UNBLOCK_USER(new UnblockUserCommand(new UserService())),
    
    /** The view lot await approval. */
    VIEW_LOT_AWAIT_APPROVAL(new ViewLotAwaitApproval(new LotService())),
    
    /** The view user set. */
    VIEW_USER_SET(new ViewUserSet(new UserService())),
    
    /** The view add auction. */
    VIEW_ADD_AUCTION(new ViewAddAuction()),
    
    /** The view auction set. */
    VIEW_AUCTION_SET(new ViewAuctionSet(new AuctionService())),
    
    /** The view lot set. */
    VIEW_LOT_SET(new ViewLotSet(new LotService())),
    
    /** The view lot. */
    VIEW_LOT(new ViewLot(new LotService())),
    
    /** The view user data. */
    VIEW_USER_DATA(new ViewUserData(new UserService())),
	
    /** The empty command. */
    EMPTY_COMMAND(new EmptyCommand()), 
    
    /** The change language. */
    CHANGE_LANGUAGE(new ChangeLanguageCommand()), 
    
    /** The approve lot. */
    APPROVE_LOT(new ApproveLotCommand(new LotService())),
    
    /** The unapprove lot. */
    UNAPPROVE_LOT(new UnApproveLotCommand(new LotService()));

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
