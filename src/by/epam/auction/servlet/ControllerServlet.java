package by.epam.auction.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.factory.CommandFactory;
import by.epam.auction.command.page.PageList;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.dao.pool.ConnectionPool;
import by.epam.auction.dao.pool.ConnectionPoolException;

import javax.servlet.annotation.WebServlet;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8635255997129111441L;

    /**
     * Action tom perform on init.
     */
    @Override
    public void init() throws ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.initPool();
            super.init();
        } catch (ConnectionPoolException e) {
            LOG.log(Level.ERROR, "Init pool exception:" + e);
            throw new RuntimeException("Init pool exception: " + e);
        }
    }

    /**
     * Action tom perform on destroy.
     */
    @Override
    public void destroy() {
        LOG.log(Level.INFO, "Destroy pool");
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.destroyPool();
        super.destroy();
    }

    /**
     * Action to perform on request of GET method.
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ParsingValues.IS_DO_GET, true);
        processRequest(request, response);
    }

    /**
     * Action to perform on request of POST method.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.log(Level.DEBUG, "Extract all parameters from the request");
        SessionRequestContent requestContent = new SessionRequestContent(request);
        
        Command command = new CommandFactory().defineCommand(requestContent);
        Optional<PageList> pageOptional = Optional.empty();
        try {
        	pageOptional = Optional.ofNullable(command.execute(requestContent));
        } catch (CommandException e) {
        	LOG.log(Level.ERROR, "command error.");
        }
        
        LOG.log(Level.DEBUG, "Extract all request attributes from the temporary storage");
        requestContent.insertAttributes(request);
        
        if(pageOptional.isPresent()) {
        	LOG.log(Level.INFO, "Save previous page:" + pageOptional.get());
        	requestContent.insertSessionAttribute(ParsingValues.PREVIOUS_PAGE, pageOptional.get());
        } else if(requestContent.getSessionAttributeValue(ParsingValues.PREVIOUS_PAGE) != null) {
        	LOG.log(Level.INFO, "Forward back.");
        	pageOptional = Optional.of((PageList)requestContent.getSessionAttributeValue(ParsingValues.PREVIOUS_PAGE));
        } else {
        	try {
				pageOptional = Optional.ofNullable(CommandType.EMPTY_COMMAND.getCommand().execute(requestContent));
			} catch (CommandException e) {
				LOG.log(Level.ERROR, "unable to perform null command");
				response.sendError(500);
			}
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pageOptional.get().getPath());
        dispatcher.forward(request,response);
    }
}
