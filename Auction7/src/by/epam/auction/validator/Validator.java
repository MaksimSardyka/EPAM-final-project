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
	 * The method checks the session, it checks marks of the session and the request
	 * on their equality. This is to prevent the Refresh page problem.
	 *
	 * @param checkSession
	 *            the check session
	 * @param checkRequest
	 *            the check request
	 * @return true, if successful
	 */
	public boolean checkSession(String checkSession, String checkRequest) {// TODO
		boolean result = false;
		if (checkSession != null && checkRequest != null && checkSession.equals(checkRequest)) {
			result = true;
		} else {
			LOG.log(Level.DEBUG, "checkSession = " + checkSession + " is not equals checkRequest = " + checkRequest);
		}
		return result;
	}
	
	public void checkCredentials(Role requiredRole, SessionRequestContent requestContent) throws AuthenticationException {
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if(user == null || user.getRole() == null || user.getRole() != requiredRole) {
			throw new AuthenticationException("Unauzorized command atempt by user: " + user.getId());
		}
	}
}
