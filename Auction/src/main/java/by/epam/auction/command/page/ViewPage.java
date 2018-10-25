package by.epam.auction.command.page;

/**
 * The Enum ViewPage.
 */
public enum ViewPage {
    
    /** The login page. */
    LOGIN_PAGE("/jsp/login/login.jsp"),
    
    /** The add auction. */
    ADD_AUCTION("/jsp/admin/add auction/add_auction.jsp"),
    
    /** The view user set. */
    VIEW_USER_SET("/jsp/admin/user set/user_set.jsp"),
    
    /** The propose lot. */
    PROPOSE_LOT("/jsp/user/propose_lot.jsp"),
    
    /** The view lot. */
    VIEW_LOT("/jsp/lot.jsp"),
    
    /** The view lot set. */
    VIEW_LOT_SET("/jsp/lot set/lot_set.jsp"),
    
    /** The view auction set. */
    VIEW_AUCTION_SET("/jsp/auction set/auction_set.jsp"),
    
    /** The view user data. */
    VIEW_USER_DATA("/jsp/user data/user_data.jsp");

    /** The path. */
    private String path;

    /**
     * Instantiates a new view page.
     *
     * @param path the path
     */
    ViewPage(final String path) {
        this.path = path;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }
}
