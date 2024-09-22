package wish.wishServer.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wish.wishServer.common.BaseTimeEntity;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String nickname;

    @Builder
    public Member(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
