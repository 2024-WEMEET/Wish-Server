package wish.wishServer.user.controller;

import org.springframework.web.bind.annotation.*;
import wish.wishServer.user.dto.TutorialUpdateDTO;
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
        String token = getTokenFromCookies(request);
        if (token == null) {
            throw new RuntimeException("JWT token not found in cookies");
        }

        String username = jwtUtil.getUsername(token);
        UserEntity userEntity = userRepository.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setRole(userEntity.getRole());
        userDTO.setTutorialCompleted(userEntity.isTutorialCompleted());
        userDTO.setUniv(userEntity.getUniv());
        userDTO.setMajor(userEntity.getMajor());
        userDTO.setYear(userEntity.getYear());
        userDTO.setSemester(userEntity.getSemester());
        userDTO.setCert(userEntity.getCert());
        userDTO.setProg(userEntity.getProg());
        userDTO.setLang(userEntity.getLang());
        userDTO.setWorries(userEntity.getWorries());

        return userDTO;
    }

    @PostMapping("/tutorial")
    public String updateTutorialInfo(@RequestBody TutorialUpdateDTO tutorialUpdateDTO, HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        if (token == null) {
            throw new RuntimeException("JWT token not found in cookies");
        }

        String username = jwtUtil.getUsername(token);
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }

        userEntity.setUniv(tutorialUpdateDTO.getUniv());
        userEntity.setMajor(tutorialUpdateDTO.getMajor());
        userEntity.setYear(tutorialUpdateDTO.getYear());
        userEntity.setSemester(tutorialUpdateDTO.getSemester());
        userEntity.setCert(tutorialUpdateDTO.getCert());
        userEntity.setProg(tutorialUpdateDTO.getProg());
        userEntity.setLang(tutorialUpdateDTO.getLang());
        userEntity.setWorries(tutorialUpdateDTO.getWorries());
        userEntity.setTutorialCompleted(tutorialUpdateDTO.isTutorialCompleted());

        userRepository.save(userEntity);

        return "Tutorial information updated successfully";
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
