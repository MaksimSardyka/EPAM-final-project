package by.epam.auction.command;

import by.epam.auction.command.exception.CommandException;
import by.epam.auction.command.page.PageList;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.validator.exception.WrongInputException;

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
     * @throws WrongInputException 
     * @throws CommandException 
     */
    PageList execute(SessionRequestContent requestContent) throws CommandException;
}
