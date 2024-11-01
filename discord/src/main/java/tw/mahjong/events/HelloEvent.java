package tw.mahjong.events;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class HelloEvent extends ListenerAdapter {
    private final List<String> handTiles = List.of("1萬", "2萬", "3萬", "4萬", "5萬", "6萬", "7萬", "8萬", "9萬");

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String tileName = event.getButton().getId();
        if (handTiles.contains(tileName)) {
            event.reply("你選擇了出牌：" + tileName)
                    .setEphemeral(true)
                    .queue();
            // TODO: 在這裡處理出牌邏輯
        } else {
            event.reply("無效的操作！").setEphemeral(true).queue();
        }
    }
}
