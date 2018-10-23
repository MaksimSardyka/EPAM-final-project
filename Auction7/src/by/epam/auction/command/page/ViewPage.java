package by.epam.auction.command.page;

public enum ViewPage {
    LOGIN_PAGE("/jsp/login/login.jsp"),
    ADD_AUCTION("/jsp/admin/add_auction.jsp"),
    VIEW_USER_SET("/jsp/admin/user_set.jsp"),
    PROPOSE_LOT("/jsp/user/propose_lot.jsp"),
    VIEW_LOT("/jsp/lot.jsp"),
    VIEW_LOT_SET("/jsp/lot set/lot_set.jsp"),
    VIEW_AUCTION_SET("/jsp/auction set/auction_set.jsp"),
    VIEW_USER_DATA("/jsp/user data/user_data.jsp");

    private String path;

    ViewPage(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
