package wish.wishServer.oauthjwt.oauth2;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wish.wishServer.oauthjwt.dto.CustomOAuth2User;
import wish.wishServer.oauthjwt.jwt.JWTUtil;
import wish.wishServer.user.entity.UserEntity;
import wish.wishServer.user.repository.UserRepository;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public CustomSuccessHandler(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        // 사용자 역할 확인
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // JWT 토큰 생성
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 60 * 60L);
        response.addCookie(createCookie("Authorization", token));

        // 튜토리얼 완료 여부 확인
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        boolean tutorialCompleted = userEntity.isTutorialCompleted();
        String name = userEntity.getName();
        
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());


        // 리디렉션 URL에 튜토리얼 완료 여부를 추가
        String redirectUrl = "http://localhost:5500/loading/?tutorialCompleted=" + tutorialCompleted + "&name=" + encodedName;
        response.sendRedirect(redirectUrl);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60 * 60);
        // cookie.setSecure(true); // HTTPS에서만 사용 가능
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
