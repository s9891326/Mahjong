package tw.mahjong.discord.components;

import lombok.NoArgsConstructor;

import java.util.EnumMap;

enum UserInfoField {
    CHANNEL_ID, GAME_ID
}

@NoArgsConstructor
public class UserInfo {
    private final EnumMap<UserInfoField, String> fields = new EnumMap<>(UserInfoField.class);

    public String get(UserInfoField field) {
        return fields.get(field);
    }

    public void setChannel(String value) {
        fields.put(UserInfoField.CHANNEL_ID, value);
    }

    public void setGameId(String value) {
        fields.put(UserInfoField.GAME_ID, value);
    }
}
