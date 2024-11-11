package tw.mahjong.discord.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import tw.mahjong.app.Presenter;
import tw.mahjong.app.repository.Repository;
import tw.mahjong.app.usecases.JoinGameUsecase;
import tw.mahjong.discord.components.UserInfoComponent;
import tw.mahjong.discord.config.MessageConfig;
import tw.mahjong.discord.presenter.JoinGamePresenter;
import tw.mahjong.domain.events.JoinEvent;
import tw.mahjong.domain.exceptions.MahjongException;

import java.util.EnumSet;
import java.util.Objects;

@AllArgsConstructor
public class JoinCommandHandler implements CommandHandler {
    private final Repository repository;
    private final UserInfoComponent userInfoComponent;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String gameId = Objects.requireNonNull(event.getOption("gameid")).getAsString();
        if (gameId.isEmpty()) {
            event.reply("Invalid Game ID").queue();
            return;
        }

        JoinGameUsecase joinGameUsecase = new JoinGameUsecase(repository);
        Presenter presenter = new JoinGamePresenter();
        joinGameUsecase.execute(joinGameUsecase.input(gameId, event.getUser().getName()), presenter);
        JoinEvent botModel = (JoinEvent) presenter.asBotModel();
        if (botModel.isGameStarted) {
            event.reply("Game start").queue();
            createUserChannel(event, gameId);
        } else {
            event.reply(String.valueOf(botModel.success)).queue();
        }
        userInfoComponent.addUserAndGameId(event.getUser(), gameId);
    }

    private void createUserChannel(SlashCommandInteractionEvent event, String gameId) {
        Guild guild = event.getGuild();
        if (guild == null) {
            throw new MahjongException(MessageConfig.systemError);
        }

        for (User user : userInfoComponent.getUserByGameId(gameId)) {
            Member m = guild.getMember(user);
            if (m == null) {
                throw new MahjongException(MessageConfig.systemError);
            }

            guild.createTextChannel(user.getName() + "-private")
                    .addPermissionOverride(guild.getPublicRole(), EnumSet.noneOf(Permission.class), EnumSet.of(Permission.VIEW_CHANNEL))
                    .addPermissionOverride(m, EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.noneOf(Permission.class))
                    .addPermissionOverride(guild.getSelfMember(), EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.noneOf(Permission.class))
                    .queue(channel -> {
                        channel.sendMessage("這是您的私密遊戲頻道").queue();
                        userInfoComponent.addUserAndChannelId(user, channel.getId());
                    });
        }
    }
}
