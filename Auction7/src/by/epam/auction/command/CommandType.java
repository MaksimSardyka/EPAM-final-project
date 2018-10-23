package by.epam.auction.command;

import by.epam.auction.command.administrator.BlockUserCommand;
import by.epam.auction.command.administrator.UnblockUserCommand;
import by.epam.auction.command.lot.BidCommand;
import by.epam.auction.command.lot.ProposeLotCommand;
import by.epam.auction.command.administrator.CreateAuctionCommand;
import by.epam.auction.command.administrator.ViewLotAwaitApproval;
import by.epam.auction.command.administrator.ApproveLotCommand;
import by.epam.auction.command.page.ViewLot;
import by.epam.auction.command.page.ViewLotSet;
import by.epam.auction.command.page.ViewProposeLot;
import by.epam.auction.command.user.LoginCommand;
import by.epam.auction.command.user.LogoutCommand;
import by.epam.auction.command.user.RegisterCommand;
import by.epam.auction.command.user.UpdateUserCommand;
import by.epam.auction.command.user.AddFundsCommand;
import by.epam.auction.command.user.WithdrawFundsCommand;
import by.epam.auction.command.page.ViewUserSet;
import by.epam.auction.command.page.ViewUserData;
import by.epam.auction.command.page.ViewAddAuction;
import by.epam.auction.command.page.ViewAuctionSet;
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
    UPDATE_USER(new UpdateUserCommand(new UserService())),
    ADD_FUNDS(new AddFundsCommand(new UserService())),
    WITHDRAW_FUNDS(new WithdrawFundsCommand(new UserService())),
    BID(new BidCommand(new LotService())),
    
    VIEW_PROPOSE_LOT(new ViewProposeLot(new LotService())),
    PROPOSE_LOT(new ProposeLotCommand(new LotService())),
    
    CREATE_AUCTION(new CreateAuctionCommand(new AuctionService())),
    BLOCK_USER(new BlockUserCommand(new UserService())),
    UNBLOCK_USER(new UnblockUserCommand(new UserService())),
    
    VIEW_LOT_AWAIT_APPROVAL(new ViewLotAwaitApproval(new LotService())),
    VIEW_USER_SET(new ViewUserSet(new UserService())),
    VIEW_ADD_AUCTION(new ViewAddAuction()),
    VIEW_AUCTION_SET(new ViewAuctionSet(new AuctionService())),
    VIEW_LOT_SET(new ViewLotSet(new LotService())),
    VIEW_LOT(new ViewLot(new LotService())),
    VIEW_USER_DATA(new ViewUserData(new UserService())),
	
    EMPTY_COMMAND(new EmptyCommand()), 
    CHANGE_LANGUAGE(new ChangeLanguageCommand()), 
    APPROVE_LOT_COMMAND(new ApproveLotCommand(new LotService()));

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
