package tw.mahjong.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put("start", new StartCommand());
        commands.put("draw", new DrawCommand());
        // 添加其他指令
    }

    public void handleCommand(MessageReceivedEvent event) {
        String[] messageParts = event.getMessage().getContentRaw().split(" ");
        String commandName = messageParts[0].substring(1); // 去掉前面的「/」
        String[] args = Arrays.copyOfRange(messageParts, 1, messageParts.length);

        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(event, args);
        } else {
            // 處理未知指令
        }
    }
}
