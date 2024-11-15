package wish.wishServer.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String name;
    private String email;
    private String role;
    private boolean tutorialCompleted;

    private String univ;      // 대학 이름
    private String major;     // 전공
    private Integer year;      // 학년
    private Integer semester;  // 학기

    private List<String> cert;  // 자격증 리스트
    private List<String> prog;  // 프로그램 리스트
    private List<String> lang;  // 어학 성적 리스트
    private List<Integer> worries; // 고민 리스트
}
