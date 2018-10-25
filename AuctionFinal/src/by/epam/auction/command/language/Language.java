package by.epam.auction.command.language;

/**
 * The Enum Language.
 */
public enum Language {
	
	/** The en us. */
	EN_US("en_US"),
	
	/** The ru ru. */
	RU_RU("ru_RU"),
    
    /** The de de. */
    DE_DE("de_DE");
	
	/**
	 * Instantiates a new language.
	 *
	 * @param locale the locale
	 */
	Language(String locale){
		this.locale = locale;
	}
	
	/** The locale. */
	String locale;
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public String getLocale(){
		return locale;
	}
}
