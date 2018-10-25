package by.epam.auction.customtag;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * The Class {@code AuctionEnd} is to handle the custom tag that calculates the
 * auction end time.
 */
@SuppressWarnings("serial")
public class AuctionEnd extends TagSupport {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The start. */
	private LocalDateTime start;

	/** The finish. */
	private LocalDateTime finish;

	/**
	 * Sets the start.
	 *
	 * @param start
	 *            the new start
	 */
	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	/**
	 * Sets the finish.
	 *
	 * @param finish
	 *            the new finish
	 */
	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	/**
	 * Handles the custom tag that calculates the auction end time.
	 */
	@Override
	public int doStartTag() {
		try {
			if (start != null && finish != null && start.isBefore(LocalDateTime.now())
					&& finish.isAfter(LocalDateTime.now())) {
				long millis = Duration.between(LocalDateTime.now(), finish).toMillis();
				pageContext.getOut().write(String.valueOf(millis));
			} else {
				pageContext.getOut().write("0");
			}
		} catch (IOException | NumberFormatException | DateTimeParseException e) {
			LOG.log(Level.ERROR, "\nEx.:" + e.getMessage() + "\n in the custom tag process with start time = " + start
					+ ", finish time = " + finish);
		}
		return SKIP_BODY;
	}
}
