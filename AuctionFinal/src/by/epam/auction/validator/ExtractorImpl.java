package by.epam.auction.validator;

import java.util.Optional;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Class ExtractorImpl.
 *
 * @param <T> the generic type
 */
public class ExtractorImpl<T> implements Extractor<T>{

	/**
	 * Extracts data from {@link SessionRequestContent}
	 */
	@Override
	public T extract(Parser<T> parser, String name, SessionRequestContent requestContent) throws WrongInputException {
		Optional<T> valueOptional = Optional.empty();
			String [] values = requestContent.getRequestParameter(name);
			if(values != null 
					&& !values[0].toLowerCase().contains(RegExpAndPatternHolder.SCRIPT)
					&& !values[0].contains(RegExpAndPatternHolder.LEFT_CHEVRON)
					&& !values[0].contains(RegExpAndPatternHolder.RIGHT_CHEVRON)) {
				valueOptional = Optional.ofNullable(parser.parse(values[0]));
			}
		return valueOptional.orElseThrow(() -> new WrongInputException("Wrong " + name + " value provided"));
	}
}
