package by.epam.auction.constant;

/**
 * The Class {@code RegExpAndPatternHolder} contains the project regular expressions and
 * pattern.
 */
public final class RegExpAndPatternHolder {
	public static final String LOGIN_REG_EX = "^[\\w]{6,40}$";
	public static final String EMAIL_REG_EX = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
	public static final String PASSWORD_REG_EX = "(?=^.{6,40}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
	public static final String MONEY_AMOUNT_REG_EX = "^\\d{0,10}(\\.\\d{1,2})?$";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

	/** The Constant REG_IMG. */
	public static final String REG_IMG = "[\\w]+\\.((jpg)|(png))";
}
