package by.epam.auction.validator.parser;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.validator.Parser;
import by.epam.auction.validator.exception.WrongInputException;

public class PageNumberParser implements Parser<Long>{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	public Long parse(String pageNumber) throws WrongInputException{
		LOG.log(Level.DEBUG, "Parse page number: " + pageNumber);
		
	    return Optional.of(pageNumber)
				.filter(p -> p!=null)
				.filter(p-> !p.trim().isEmpty())
	            .map(p -> {
	            	try { 
	            		return Long.valueOf(p.trim());
	            	} catch (NumberFormatException  e) { 
	            		LOG.log(Level.ERROR, e.getMessage());
	            		return null;
	            	}
	            })
	            .filter(p -> p!=null)
	            .filter(p -> p>=0)
	            .orElseThrow(() -> new WrongInputException("Non-valid page number provided:" + pageNumber));
	}
}
