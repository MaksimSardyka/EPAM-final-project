package by.epam.auction.validator;

import java.util.Optional;

import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

public class ExtractorImpl<T> implements Extractor<T>{

	@Override
	public T extract(Parser<T> parser, String name,SessionRequestContent requestContent) throws WrongInputException {
		Optional<T> valueOptional = Optional.empty();
			String [] values = requestContent.getRequestParameter(name);
			if(values != null) {
				valueOptional = Optional.ofNullable(parser.parse(values[0]));
			}
		return valueOptional.orElseThrow(() -> new WrongInputException("Wrong " + name + " value provided"));
	}
}
