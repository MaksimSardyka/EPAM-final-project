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
import by.epam.auction.command.factory.CommandFactory;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.dao.pool.ConnectionPool;
import by.epam.auction.dao.pool.ConnectionPoolException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet("/controller")
@MultipartConfig
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
        
        Optional<ViewPage> previousPageOptional = Optional.empty();
        try {
        	previousPageOptional = Optional.ofNullable((ViewPage)requestContent.getSessionAttributeValue(ParsingValues.PREVIOUS_PAGE));
        } catch (ClassCastException e){
        	LOG.log(Level.ERROR, "Wrong previous page value provided");
        }
        
        Command command = new CommandFactory().defineCommand(requestContent);
        Optional<ViewPage> nextPageOptional = Optional.ofNullable(command.execute(requestContent));
        
        LOG.log(Level.DEBUG, "Extract all request attributes from the temporary storage");
        requestContent.insertAttributes(request);
        
        if(nextPageOptional.isPresent()) {
        	LOG.log(Level.INFO, "Save previous page:" + nextPageOptional.get());
        	requestContent.insertSessionAttribute(ParsingValues.PREVIOUS_PAGE, nextPageOptional.get());
        } else if(previousPageOptional.isPresent()) {
        	LOG.log(Level.INFO, "Forward back to previous page.");
        	nextPageOptional = previousPageOptional;
        } else {
        	nextPageOptional = Optional.of(CommandType.EMPTY_COMMAND.getCommand().execute(requestContent));
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPageOptional.get().getPath());
        dispatcher.forward(request,response);
    }
}
