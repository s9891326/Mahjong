package tw.mahjong.discord.command;

import tw.mahjong.app.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class CommandHandlerFactory {
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    public CommandHandlerFactory(Repository repository, Map<String, String> userWithGameId) {
        commandHandlers.put("create", new CreateCommandHandler(repository, userWithGameId));
        commandHandlers.put("join", new JoinCommandHandler(repository, userWithGameId));
        commandHandlers.put("start", new StartCommandHandler(repository, userWithGameId));
        commandHandlers.put("status", new StatusCommandHandler(repository, userWithGameId));
        commandHandlers.put("draw", new DrawCommandHandler());
    }

    public CommandHandler getHandler(String commandName) {
        return commandHandlers.getOrDefault(commandName, new UnknownCommandHandler());
    }
}
