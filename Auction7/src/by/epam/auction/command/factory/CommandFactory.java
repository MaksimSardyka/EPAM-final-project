package by.epam.auction.command.factory;

import static by.epam.auction.constant.ParsingValues.COMMAND;
import static by.epam.auction.constant.ParsingValues.WRONG_ACTION;
import static by.epam.auction.constant.ParsingValues.IS_DO_GET;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.content.SessionRequestContent;

/**
 * This class is used to define type of command.
 */
public class CommandFactory {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Define procedure to perform based on user's request.
     *
     * @param requestContent
     *            User's request content stored in
     *            {@link by.epam.auction.content.SessionRequestContent}
     * @return Defined command.
     */
    public Command defineCommand(final SessionRequestContent requestContent) {
        String commandName = null;

        if (requestContent.getRequestParameter(COMMAND) != null
                && !requestContent.getRequestParameter(COMMAND)[0].isEmpty()
                && requestContent.getRequestAttributeValue(IS_DO_GET) == null) {
            commandName = requestContent.getRequestParameter(COMMAND)[0];
            LOG.log(Level.DEBUG,
                    "Extracted command parameter value is:" + commandName);
        }

        Optional<Command> resultCommand = Optional.empty();
        if (commandName != null) {
            try {
                CommandType type = CommandType.valueOf(commandName.toUpperCase());
                resultCommand = Optional.of(type.getCommand());
                LOG.log(Level.DEBUG,
                        "Defined command name is:" + commandName.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOG.log(Level.ERROR, "Illegal type of command: " + commandName);
                requestContent.insertRequestAttribute(WRONG_ACTION,
                        commandName + "unknown command");
            }
        }

        Command definedCommand = resultCommand
                .orElse(CommandType.EMPTY_COMMAND.getCommand());
        return definedCommand;
    }
}
