package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.GetStatusUsecase;
import tw.mahjong.discord.presenter.GetStatusPresenter;
import tw.mahjong.domain.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class StatusCommandHandler implements CommandHandler {
    private final Repository repository;
    private final Map<String, String> userWithGameId;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String playerName = event.getUser().getName();
        String gameId = userWithGameId.get(playerName);
        GetStatusUsecase getStatusUsecase = new GetStatusUsecase(repository);
        Presenter presenter = new GetStatusPresenter();
        getStatusUsecase.execute(getStatusUsecase.input(gameId, playerName), presenter);
        GetStatusPresenter.GetStatusBotModel botModel = (GetStatusPresenter.GetStatusBotModel) presenter.asBotModel(playerName);
        event.reply("當前牌型")
                .setEphemeral(true)
                .addComponents(createTileButtons(botModel.getHandTile()))
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
