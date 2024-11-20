package wish.wishServer.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String role;
    private boolean tutorialCompleted = false; // 튜토리얼 완료 여부

    private String univ;      // 대학 이름
    private String major;     // 전공
    private Integer year;      // 학년
    private Integer semester;  // 학기

    @ElementCollection
    private List<String> cert; // 자격증 리스트

    @ElementCollection
    private List<String> prog; // 프로그램 리스트

    @ElementCollection
    private List<String> lang; // 어학 성적 리스트

    @ElementCollection
    private List<Integer> worries; // 고민 (숫자 목록)
}
