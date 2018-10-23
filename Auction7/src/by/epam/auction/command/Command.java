package by.epam.auction.command;

import by.epam.auction.command.page.ViewPage;
import by.epam.auction.content.SessionRequestContent;

/**
 * Common command interface.
 */
public interface Command {
    /**
     * Executes provided command.
     *
     * @param requestContent
     *            Request content containing command name.
     * @return path to next jsp page.
     */
    ViewPage execute(SessionRequestContent requestContent);
}
