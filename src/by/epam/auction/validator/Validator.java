package by.epam.auction.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * User'input validator.
 */
public class Validator {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	// /**
	// * Img Reg_Ex..
	// */
	// public static final String REG_IMG = "[а-яА-Я\\w_\\-]+\\.((jpg)|(png))";

	// /**
	// * Date/time Reg_Ex.
	// */
	// public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm";

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
			LOG.log(Level.ERROR, "Provided passwords didn't match each other.");
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
			LOG.log(Level.ERROR, "checkSession = " + checkSession + " is not equals checkRequest = " + checkRequest);
		}
		return result;
	}
}
