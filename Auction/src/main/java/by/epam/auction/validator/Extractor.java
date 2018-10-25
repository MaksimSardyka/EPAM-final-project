package by.epam.auction.validator;

import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Interface Extractor.
 *
 * @param <T> the generic type
 */
public interface Extractor<T> {
	
	/**
	 * Extract.
	 *
	 * @param parser the parser
	 * @param name the name
	 * @param requestContent the request content
	 * @return the t
	 * @throws WrongInputException the wrong input exception
	 */
	T extract(Parser<T> parser, String name, SessionRequestContent requestContent) throws WrongInputException;
}
