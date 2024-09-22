package wish.wishServer.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import wish.wishServer.member.domain.Member;

@Builder
public record MemberResponse(@NotNull String username, String nickname) {

    public static MemberResponse toMember (Member member) {
        return MemberResponse.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }
}
