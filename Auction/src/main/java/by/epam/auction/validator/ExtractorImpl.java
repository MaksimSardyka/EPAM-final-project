package by.epam.auction.validator;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.RegExpAndPatternHolder;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

/**
 * The Class ExtractorImpl.
 *
 * @param <T> the generic type
 */
public class ExtractorImpl<T> implements Extractor<T>{

	private static final Logger LOG = LogManager.getLogger();

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
			LOG.log(Level.DEBUG, "values = " + values);
		return valueOptional.orElseThrow(() -> new WrongInputException("Wrong " + name + " value provided"));
	}
}
