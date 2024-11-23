package wish.wishServer.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChattingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false)
    private String username; // 유저 이름

    @Column(nullable = false, length = 1000)
    private String message; // 메시지 내용

    @Column(nullable = false)
    private boolean isFromAI; // AI 메시지 여부

    @Column(nullable = false)
    private LocalDateTime timestamp; // 대화 시간
}
