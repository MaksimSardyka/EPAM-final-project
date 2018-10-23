package by.epam.auction.command;

public enum Language {
	EN_US("en_US"),
	RU_RU("ru_RU"),
    DE_DE("de_DE");
	
	Language(String locale){
		this.locale = locale;
	}
	
	String locale;
	
	String getLocale(){
		return locale;
	}
}
