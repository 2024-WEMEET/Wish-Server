package wish.wishServer.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberSaveDto (@NotNull String username, String nickname) {
}
