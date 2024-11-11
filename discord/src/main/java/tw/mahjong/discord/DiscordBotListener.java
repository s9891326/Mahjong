package tw.mahjong.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.mahjong.discord.command.CommandHandler;
import tw.mahjong.discord.command.CommandHandlerFactory;
import tw.mahjong.discord.components.UserInfoComponent;
import tw.mahjong.discord.repository.Common;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.List;

@Component
public class DiscordBotListener extends ListenerAdapter {
    private final CommandHandlerFactory commandHandlerFactory;
    private Guild guild;
    @Autowired
    private UserInfoComponent userInfoComponent;

    public DiscordBotListener(UserInfoComponent userInfoComponent) {
        this.userInfoComponent = userInfoComponent;
        commandHandlerFactory = new CommandHandlerFactory(Common.getRepository(), userInfoComponent);
        // 註冊關閉鉤子
        Runtime.getRuntime().addShutdownHook(new Thread(this::deletePrivateChannels));
    }

    private void deletePrivateChannels() {
        for (String channelId : userInfoComponent.getAllChannelId()) {
            TextChannel privateChannel = guild.getTextChannelById(channelId);
            if (privateChannel != null) {
                privateChannel.delete().queue();
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//        User user = event.getUser();
        guild = event.getGuild();
//        Member m = guild.getMember(user);
//        guild.getTextChannelsByName(user.getName() + "-private", true).get(0).delete().queue();
//        event.getUser().openPrivateChannel().queue(c -> {
//            c.sendMessage("hello").queue();
//        });
        CommandHandler handler = commandHandlerFactory.getHandler(event.getName());
        try {
            handler.handle(event);
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
//    public void onReady(ReadyEvent event) {
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
