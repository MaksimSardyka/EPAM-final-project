package by.epam.auction.validator;

import by.epam.auction.validator.exception.WrongInputException;

public interface Parser<T> {
	T parse(String name) throws WrongInputException;
}
