package by.epam.auction.constant;

/**
 * The Class {@code RegExpAndPatternHolder} contains the project regular expressions and
 * pattern.
 */
public final class RegExpAndPatternHolder {
	
	/** The Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";
	
	/** The Constant LOGIN_REG_EX. */
	public static final String LOGIN_REG_EX = "^[\\w]{6,40}$";
	
	/** The Constant EMAIL_REG_EX. */
	public static final String EMAIL_REG_EX = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
	
	/** The Constant PASSWORD_REG_EX. */
	public static final String PASSWORD_REG_EX = "(?=^.{6,40}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
	
	/** The Constant MONEY_AMOUNT_REG_EX. */
	public static final String MONEY_AMOUNT_REG_EX = "^\\d{0,10}(\\.\\d{1,2})?$";
	
	/** The Constant DATE_TIME_PATTERN. */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

	/** The Constant REG_IMG. */
	public static final String IMG_REG_EX = "[\\w]+\\.((jpg)|(png))";
	
	/** The Constant LEFT_CHEVRON. */
	public static final String LEFT_CHEVRON = "<";
	
	/** The Constant RIGHT_CHEVRON. */
	public static final String RIGHT_CHEVRON = ">";
	
	/** The Constant SCRIPT. */
	public static final String SCRIPT = "script";
	
}
