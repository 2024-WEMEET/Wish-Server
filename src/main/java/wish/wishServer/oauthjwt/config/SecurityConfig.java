package wish.wishServer.oauthjwt.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import wish.wishServer.oauthjwt.jwt.JWTFilter;
import wish.wishServer.oauthjwt.jwt.JWTUtil;
import wish.wishServer.oauthjwt.oauth2.CustomSuccessHandler;
import wish.wishServer.oauthjwt.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final CustomOAuth2UserService customOAuth2UserService;
        private final CustomSuccessHandler customSuccessHandler;
        private final JWTUtil jwtUtil;

        public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                        CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil) {
                this.customOAuth2UserService = customOAuth2UserService;
                this.customSuccessHandler = customSuccessHandler;
                this.jwtUtil = jwtUtil;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .cors(corsCustomizer -> corsCustomizer
                                                .configurationSource(new CorsConfigurationSource() {
                                                        @Override
                                                        public CorsConfiguration getCorsConfiguration(
                                                                        HttpServletRequest request) {
                                                                CorsConfiguration configuration = new CorsConfiguration();
                                                                configuration.setAllowedOriginPatterns(
                                                                                Collections.singletonList(
                                                                                                "http://localhost:5500")); //프론트 주소
                                                                configuration.setAllowedMethods(
                                                                                Collections.singletonList("*"));
                                                                configuration.setAllowCredentials(true);
                                                                configuration.setAllowedHeaders(
                                                                                Collections.singletonList("*"));
                                                                configuration.setMaxAge(3600L);
                                                                configuration.setExposedHeaders(Collections
                                                                                .singletonList("Authorization"));
                                                                return configuration;
                                                        }
                                                }))
                                .csrf((auth) -> auth.disable()) // CSRF 비활성화
                                .formLogin((auth) -> auth.disable()) // Form 로그인 비활성화
                                .httpBasic((auth) -> auth.disable()) // HTTP Basic 인증 비활성화
                                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
                                .oauth2Login((oauth2) -> oauth2
                                                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                                                .userService(customOAuth2UserService))
                                                .successHandler(customSuccessHandler))
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/", "/public/**").permitAll() // 모두 허용 메소드
                                                .requestMatchers("/member/userInfo", "/my").hasRole("USER") // 권한 필요 메소드
                                                .anyRequest().authenticated())
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 관리 STATELESS

                return http.build();
        }
}
