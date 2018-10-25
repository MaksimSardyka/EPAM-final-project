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

import by.epam.auction.constant.RegExpAndPatternHolder;

/**
 * The Class {@code CharsetFilter} implements the {@code Filter}. It is to
 * convert each request and response data to the definite encoding.
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class CharsetFilter implements Filter {

	/** Encoding to set. */
	private String code;

	/**
	 * The overridden method init(FilterConfig fConfig)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		code = fConfig.getInitParameter(RegExpAndPatternHolder.ENCODING);
	}

	/**
	 * The overridden method doFilter(ServletRequest request, ServletResponse
	 * response, FilterChain chain), it checks and converts encoding of request
	 * and response to the definite one.
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String codeRequest = request.getCharacterEncoding();
		if (code != null && !code.equalsIgnoreCase(codeRequest)) {
			request.setCharacterEncoding(code);
			response.setCharacterEncoding(code);
		}

		chain.doFilter(request, response);
	}

	/**
	 * The overridden method destroy()
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		code = null;
	}
}
