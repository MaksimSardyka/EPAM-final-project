package by.epam.auction.validator;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;

/**
 * User'input validator.
 */
public class Validator {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Validate 2 passwords on their equality.
	 *
	 * @param password1
	 *            Firts password.
	 * @param password2
	 *            Second password.
	 * @return {@code true}, they matches each other.
	 */
	public boolean passwordMatchValidate(String password1, String password2) {
		boolean result = false;
		if (password1 != null && password2 != null && password1.equals(password2)) {
			result = true;
		} else {
			LOG.log(Level.DEBUG, "Provided passwords didn't match each other.");
		}
		return result;
	}
	
	/**
	 * Check credentials.
	 *
	 * @param requiredRole the required role
	 * @param requestContent the request content
	 * @throws AuthenticationException the authentication exception
	 */
	public void checkCredentials(Role requiredRole, SessionRequestContent requestContent) throws AuthenticationException {
		if(requestContent == null) {
			throw new AuthenticationException("null requestContent provided");
		}
		if(requiredRole == null) {
			throw new AuthenticationException("null requiredRole provided");
		}
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if(user == null || user.getRole() == null || user.getRole() != requiredRole) {
			if(user != null && user.getId() != null) {
				throw new AuthenticationException("Unauzorized command atempt by user: " + user.getId());
			} else {
				throw new AuthenticationException("Unauzorized command atempt.");
			}
		}
	}
}
