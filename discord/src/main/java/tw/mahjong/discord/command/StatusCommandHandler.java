package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.GetStatusUsecase;
import tw.mahjong.discord.components.UserInfoComponent;
import tw.mahjong.discord.presenter.GetStatusPresenter;
import tw.mahjong.domain.Player;
import tw.mahjong.domain.Tile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class StatusCommandHandler implements CommandHandler {
    private final Repository repository;
    private final UserInfoComponent userInfoComponent;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String embedTemp = "手牌: %s, 門前: %s";
        String playerName = event.getUser().getName();
        String gameId = userInfoComponent.getGameId(event.getUser());
        GetStatusUsecase getStatusUsecase = new GetStatusUsecase(repository);
        Presenter presenter = new GetStatusPresenter();
        getStatusUsecase.execute(getStatusUsecase.input(gameId, playerName), presenter);
        GetStatusPresenter.GetStatusBotModel botModel = (GetStatusPresenter.GetStatusBotModel) presenter.asBotModel(playerName);
//        event.reply("當前牌型")
//                .setEphemeral(true)
//                .addComponents(createTileButtons(botModel.getHandTile()))
//                .queue();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("麻將牌桌狀況")
                .addField("棄牌堆", botModel.getDeck().getDiscardTile().toString(), false)
                .addField("牌池", String.valueOf(botModel.getDeck().getTile().size()), false);

        for (Player player : botModel.getRound().getPlayers()) {
//            if (player.getName().equals(playerName)) {
//                continue;
//            }
            embedBuilder.addField(
                    player.getName(),
                    String.format(embedTemp, player.getHandTile().size(), player.getDoorFront().toString()),
                    false
            );
        }

        embedBuilder.addField(
                playerName,
                String.format(embedTemp, botModel.getHandTile().toString(), botModel.getDoorFront().toString()),
                false
        );

        event.replyEmbeds(embedBuilder.build())
                .addActionRow(Button.primary("draw", "摸牌"), Button.danger("discard", "出牌"))
                .setEphemeral(true)
                .queue();
    }

    private List<LayoutComponent> createTileButtons(List<Tile> handTiles) {
        List<LayoutComponent> layout = new ArrayList<>();
        List<Button> rowButtons = new ArrayList<>();

        for (int i = 0; i < handTiles.size(); i++) {
            rowButtons.add(Button.primary(handTiles.get(i).toString() + i, handTiles.get(i).toString()));

            // 每 5 個按鈕就加入一個 Row
            if ((i + 1) % 5 == 0 || i == handTiles.size() - 1) {
                layout.add(ActionRow.of(rowButtons));
                rowButtons = new ArrayList<>();
            }
        }
        return layout;
    }
}
