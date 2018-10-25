package by.epam.auction.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.auction.constant.ParsingValues;
import by.epam.auction.constant.RegExpAndPatternHolder;

/**
 * Class {@code JspRedirectFilter} implements the {@link Filter}. It is to
 * handle the unauthorized direct access to the JSP and redirect the response
 * to the index page.
 */
@WebFilter(urlPatterns = { "/jsp/*" }, initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class JspRedirectFilter implements Filter {

	/** The index path. */
	private String indexPath;

	/**
	 * The overridden method init(FilterConfig fConfig)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter(RegExpAndPatternHolder.INDEX_PATH);
	}

	/**
	 * The overridden method doFilter(ServletRequest request, ServletResponse
	 * response, FilterChain chain)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpRequest.getSession().removeAttribute(ParsingValues.USER);
		httpRequest.getSession().removeAttribute(ParsingValues.PREVIOUS_PAGE);
		httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
		chain.doFilter(request, response);
	}

	/**
	 * The overridden method destroy()
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}
}
