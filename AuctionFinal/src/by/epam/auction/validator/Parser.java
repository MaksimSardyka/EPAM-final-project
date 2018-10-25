package by.epam.auction.validator;

import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Interface Parser.
 *
 * @param <T> the generic type
 */
public interface Parser<T> {
	
	/**
	 * Parses the.
	 *
	 * @param name the name
	 * @return the t
	 * @throws WrongInputException the wrong input exception
	 */
	T parse(String name) throws WrongInputException;
}
