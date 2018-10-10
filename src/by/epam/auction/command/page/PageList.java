package by.epam.auction.command.page;

public enum PageList {

    ADMIN_PAGE("/jsp/fragment/admin/admin_panel.jsp"),

    LOGIN_PAGE("/jsp/login.jsp"),

    LOT_PAGE("/jsp/lot.jsp"),
    
    AUCTION_PAGE("/jsp/fragment/user/auction_set_page.jsp"),

    LOT_SET_PAGE("/jsp/fragment/user/lot_set_page.jsp"),
    
    NULL_PAGE("/jsp/login.jsp");


    private String path;

    PageList(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
