package tw.mahjong.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class BotCommand extends ListenerAdapter {
    private CommandManager commandManager = new CommandManager();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        System.out.println("slash");
        switch (event.getName()) {
            case "startgame" -> {
                OptionMapping modeOption = event.getOption("mode");
                String gameMode = modeOption != null ? modeOption.getAsString() : "default";

                // 處理 startgame 邏輯
                event.reply("Starting a new Mahjong game in mode: " + gameMode).queue();
            }
            case "draw" -> {
                // 處理 draw 邏輯
                event.reply("Drawing a tile for you!").queue();
            }
            case "join" -> {
                event.reply("join!").queue();
            }
            default -> event.reply("Unknown command!").queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // 確保訊息來自文字頻道，且不是來自 bot 自己
        if (event.isFromGuild() && !event.getAuthor().isBot()) {
            commandManager.handleCommand(event);

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
        }
    }


    private final List<String> handTiles = List.of("1萬", "2萬", "3萬", "4萬", "5萬", "6萬", "7萬", "8萬", "9萬");

//    @Override
//    public void onReady(net.dv8tion.jda.api.events.session.ReadyEvent event) {
//        TextChannel channel = event.getJDA().getTextChannelById("1301098117122228226");
//        if (channel != null) {
//            channel.sendMessage("請選擇一張牌來出牌：")
//                    .setComponents(createTileButtons())
//                    .queue();
//        }
//    }

    private List<LayoutComponent> createTileButtons() {
        List<LayoutComponent> rows = new ArrayList<>();
        List<Button> rowButtons = new ArrayList<>();

        for (int i = 0; i < handTiles.size(); i++) {
            rowButtons.add(Button.primary(handTiles.get(i), handTiles.get(i)));

            // 每 5 個按鈕就加入一個 Row
            if ((i + 1) % 5 == 0 || i == handTiles.size() - 1) {
                rows.add(ActionRow.of(rowButtons));
                rowButtons = new ArrayList<>();
            }
        }
        return rows;
    }
}
