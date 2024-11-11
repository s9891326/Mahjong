package tw.mahjong.discord.command;

import tw.mahjong.app.repository.Repository;
import tw.mahjong.discord.components.UserInfoComponent;

import java.util.HashMap;
import java.util.Map;

public class CommandHandlerFactory {
    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    public CommandHandlerFactory(Repository repository, UserInfoComponent userInfoComponent) {
        commandHandlers.put("create", new CreateCommandHandler(repository, userInfoComponent));
        commandHandlers.put("join", new JoinCommandHandler(repository, userInfoComponent));
        commandHandlers.put("status", new StatusCommandHandler(repository, userInfoComponent));
        commandHandlers.put("draw", new DrawCommandHandler());
    }

    public CommandHandler getHandler(String commandName) {
        return commandHandlers.getOrDefault(commandName, new UnknownCommandHandler());
    }
}
