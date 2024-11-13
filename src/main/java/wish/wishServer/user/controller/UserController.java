package wish.wishServer.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wish.wishServer.user.dto.UserDTO;
import wish.wishServer.user.entity.UserEntity;
import wish.wishServer.user.repository.UserRepository;
import wish.wishServer.oauthjwt.jwt.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/member")
public class UserController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public UserController(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/userInfo")
    public UserDTO getInfo(HttpServletRequest request) {
        // 쿠키에서 JWT 토큰 추출
        String token = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            throw new RuntimeException("JWT token not found in cookies");
        }

        // JWT 토큰에서 username 추출
        String username = jwtUtil.getUsername(token);

        // UserRepository를 사용하여 username으로 엔티티 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        // UserEntity에서 UserDTO로 변환
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(userEntity.getRole());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setTutorialCompleted(userEntity.isTutorialCompleted());

        return userDTO;
    }
}
