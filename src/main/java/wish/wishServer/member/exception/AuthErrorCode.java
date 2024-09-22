package wish.wishServer.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    MEMBER_DUPLICATION("이미 존재하는 회원입니다."),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.");

    private final String message;
}
