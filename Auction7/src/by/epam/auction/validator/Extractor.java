package by.epam.auction.validator;

import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

public interface Extractor<T> {
	T extract(Parser<T> parser, String name, SessionRequestContent requestContent) throws WrongInputException;
}
