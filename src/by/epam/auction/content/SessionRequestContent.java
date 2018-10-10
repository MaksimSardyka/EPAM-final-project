package by.epam.auction.content;

import static by.epam.auction.constant.ParsingValues.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * {@code SessionRequestContent} hold the session and the request info.
 */
public class SessionRequestContent {
    /**
     * The request attributes.
     */
    private HashMap<String, Object> requestAttributes;

    /**
     * The request parameters.
     */
    private HashMap<String, String[]> requestParameters;

    /**
     * The session attributes.
     */
    private HashMap<String, Object> sessionAttributes;

    /**
     * The request.
     */
    private HttpServletRequest request;

    /**
     * The path of the real servlet context.
     */
    private String servletContextPath;

    /**
     * Instantiates a new session request content.
     *
     * @param request
     *            the request
     */
    public SessionRequestContent(HttpServletRequest request) {
        this.request = request;
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractValues(request);
        if (request.getServletContext() != null) {
            servletContextPath = request.getServletContext()
                    .getRealPath(EMPTY_STRING);
        }
    }

    /**
     * Gets the request part.
     *
     * @return the request part
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ServletException
     *             the servlet exception
     */
    public Part getRequestPart() throws IOException, ServletException {
        return request.getPart(REQUEST_PART_NAME);
    }

    /**
     * Gets the real servlet path.
     *
     * @return the path
     */
    public String getPath() {
        return servletContextPath;
    }

    /**
     * Removes the session attribute.
     *
     * @param name
     *            the name
     */
    public void removeSessionAttribute(String name) {
        sessionAttributes.remove(name);
        request.getSession().removeAttribute(name);
    }

    /**
     * Gets the session attribute value.
     *
     * @param name
     *            the name
     * @return the session attribute value
     */
    public Object getSessionAttributeValue(String name) {
        return sessionAttributes.get(name);
    }

    /**
     * Gets the request parameter.
     *
     * @param param
     *            the param
     * @return the request parameter
     */
    public String[] getRequestParameter(String param) {
        return requestParameters.get(param);
    }

    /**
     * Gets the request attribute value.
     *
     * @param name
     *            the name
     * @return the request attribute value
     */
    public Object getRequestAttributeValue(String name) {
        return requestAttributes.get(name);
    }

    /**
     * Inserts request attribute to the content.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void insertRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    /**
     * Inserts session attribute to the content.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void insertSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    /**
     * Inserts attributes from the holding content into the request and the
     * session.
     *
     * @param request
     *            the request
     */
    public void insertAttributes(HttpServletRequest request) {
        for (HashMap.Entry<String, Object> sessionAttribute : sessionAttributes
                .entrySet()) {
            request.getSession().setAttribute(sessionAttribute.getKey(),
                    sessionAttribute.getValue());
        }
        for (HashMap.Entry<String, Object> requestAttribute : requestAttributes
                .entrySet()) {
            request.setAttribute(requestAttribute.getKey(),
                    requestAttribute.getValue());
        }
    }

    /**
     * Extracts values from the request and the session into the content.
     *
     * @param request
     *            the request
     */
    private void extractValues(HttpServletRequest request) {
        requestParameters = new HashMap<String, String[]>(
                request.getParameterMap());
        Enumeration<String> names = request.getAttributeNames();
        while (names != null && names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object value = request.getAttribute(name);
            requestAttributes.put(name, value);
        }
        if (request.getSession() != null) {
            names = request.getSession().getAttributeNames();
            while (names != null && names.hasMoreElements()) {
                String name = (String) names.nextElement();
                Object value = request.getSession().getAttribute(name);
                sessionAttributes.put(name, value);
            }
        }
    }
}
