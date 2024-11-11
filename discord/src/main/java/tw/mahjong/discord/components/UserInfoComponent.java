package tw.mahjong.discord.components;

import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserInfoComponent {
    private final Map<User, UserInfo> userInfo = new HashMap<>();

    public String getGameId(User user) {
        return userInfo.get(user).get(UserInfoField.GAME_ID);
    }

    public void addUserAndGameId(User user, String gameId) {
        UserInfo info = userInfo.get(user);
        if (info == null) {
            info = new UserInfo();
        }
        info.setGameId(gameId);
        userInfo.put(user, info);
    }

    public void addUserAndChannelId(User user, String channelId) {
        UserInfo info = userInfo.get(user);
        if (info == null) {
            info = new UserInfo();
        }
        info.setChannel(channelId);
        userInfo.put(user, info);
    }

    public List<String> getAllChannelId() {
        return userInfo.values().stream().map(info -> info.get(UserInfoField.CHANNEL_ID)).collect(Collectors.toList());
    }

    public List<User> getUserByGameId(String gameId) {
        return userInfo.entrySet().stream()
                .filter(entry -> entry.getValue().get(UserInfoField.GAME_ID).equals(gameId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
