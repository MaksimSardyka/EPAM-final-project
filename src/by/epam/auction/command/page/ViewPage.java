package by.epam.auction.command.page;

public enum ViewPage {

    ADMIN_PAGE("/jsp/fragment/admin/admin_panel.jsp"),

    LOGIN_PAGE("/jsp/login.jsp"),

    VIEW_LOT("/jsp/lot.jsp"),
    VIEW_LOT_SET("/jsp/fragment/user/lot_set_page.jsp"),
    VIEW_AUCTION_SET("/jsp/fragment/user/auction_set_page.jsp"),
    
    NULL_PAGE("/jsp/login.jsp");


    private String path;

    ViewPage(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
