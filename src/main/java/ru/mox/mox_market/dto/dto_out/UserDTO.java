package ru.mox.mox_market.dto.dto_out;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.authEnt.MoxUser;

@Builder
@Jacksonized
public record UserDTO(
        String publicName,
        String username,
        String avatarUrl,
        String status,
        String aboutMe
) {
    public static UserDTO of(MoxUser user) {
        return UserDTO.builder()
                .publicName(user.getUsername())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus())
                .aboutMe(user.getAboutMe())
                .build();
    }
}
