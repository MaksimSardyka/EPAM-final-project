package by.epam.auction.command.administrator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.PageList;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.service.AdministratorService;

public class BlockUserCommand implements Command{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    private AdministratorService service;
    
    public BlockUserCommand(AdministratorService service){
        this.service = service;
    }
    
    @Override
    public PageList execute(SessionRequestContent requestContent) {
        LOG.log(Level.DEBUG, "Perform " + CommandType.BlOCK_USER.name());
        
        //validate admin role from credentials
        //validate id of user we want to block to 
        
        //boolean userDAO.blockUser throws ServiceException
        return PageList.ADMIN_PAGE;
    }

}
