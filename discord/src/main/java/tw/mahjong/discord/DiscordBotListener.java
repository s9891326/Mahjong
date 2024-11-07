package tw.mahjong.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import tw.mahjong.discord.command.CommandHandler;
import tw.mahjong.discord.command.CommandHandlerFactory;
import tw.mahjong.discord.repository.Common;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DiscordBotListener extends ListenerAdapter {
    private final CommandHandlerFactory commandHandlerFactory;

    public DiscordBotListener() {
        Map<String, String> userWithGameId = new HashMap<>();
        commandHandlerFactory = new CommandHandlerFactory(Common.getRepository(), userWithGameId);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        CommandHandler handler = commandHandlerFactory.getHandler(event.getName());
        try {
            handler.handle(event);

            // 為了方便測試
            if (event.getName().equals("create")) {
                handler = commandHandlerFactory.getHandler("join");
                for (int i = 0; i < 3; i++) {
                    handler.handle(event);
                }
            }
        } catch (MahjongException mahjongException) {
            event.reply(mahjongException.getMessage()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // 確保訊息來自文字頻道，且不是來自 bot 自己
//        if (event.isFromGuild() && !event.getAuthor().isBot()) {
//            commandManager.handleCommand(event);

//            Message message = event.getMessage();
//            String content = message.getContentRaw();
//
//            // 判斷指令是否為 "!startgame"
//            System.out.println(event.getChannel());
//            if (content.equalsIgnoreCase("!startgame")) {
//                // 回應玩家，告訴他們遊戲已開始
//                event.getChannel().sendMessage("麻將遊戲已開始！").queue();
//
//                // 你可以在這裡加入初始化遊戲的邏輯
////                startMahjongGame();
//            }
//        }
    }

//    @Override
//    public void onReady(net.dv8tion.jda.api.events.session.ReadyEvent event) {
//        TextChannel channel = event.getJDA().getTextChannelById("1301098117122228226");
//        if (channel != null) {
//            channel.sendMessage("請選擇一張牌來出牌：")
//                    .setComponents(createTileButtons())
//                    .queue();
//        }
//    }

    private final List<String> handTiles = List.of("1萬", "2萬", "3萬", "4萬", "5萬", "6萬", "7萬", "8萬", "9萬");

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String tileName = event.getButton().getId();
        if (handTiles.contains(tileName)) {
            event.reply("你選擇了出牌：" + tileName)
                    .setEphemeral(true)
                    .queue();
        } else {
            event.reply("無效的操作！").setEphemeral(true).queue();
        }
    }
}
