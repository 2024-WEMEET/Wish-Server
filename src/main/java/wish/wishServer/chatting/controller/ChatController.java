package wish.wishServer.chatting.controller;

import org.springframework.web.bind.annotation.*;
import wish.wishServer.chatting.dto.ChatRequestDTO;
import wish.wishServer.chatting.dto.ChatResponseDTO;
import wish.wishServer.chatting.entity.ChattingEntity;
import wish.wishServer.chatting.repository.ChattingRepository;
import wish.wishServer.chatting.service.AIService;
import wish.wishServer.oauthjwt.jwt.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChattingRepository chattingRepository;
    private final JWTUtil jwtUtil;
    private final AIService aiService;

    public ChatController(ChattingRepository chattingRepository, JWTUtil jwtUtil, AIService aiService) {
        this.chattingRepository = chattingRepository;
        this.jwtUtil = jwtUtil;
        this.aiService = aiService;
    }

    @PostMapping("/send")
    public ChatResponseDTO sendMessage(@RequestBody ChatRequestDTO chatRequestDTO, HttpServletRequest request) {
        // 쿠키에서 JWT 토큰 추출
        String token = getTokenFromCookies(request);
        if (token == null) {
            throw new RuntimeException("JWT token not found in cookies");
        }

        // JWT에서 username 추출
        String username = jwtUtil.getUsername(token);

        // 유저 메시지 저장
        ChattingEntity userMessage = new ChattingEntity();
        userMessage.setUsername(username);
        userMessage.setMessage(chatRequestDTO.getMessage());
        userMessage.setFromAI(false);
        userMessage.setTimestamp(LocalDateTime.now());
        chattingRepository.save(userMessage);

        // AI 서버 호출
        String aiResponse = aiService.getAIResponse(username, chatRequestDTO.getMessage());

        // AI 메시지 저장
        ChattingEntity aiMessage = new ChattingEntity();
        aiMessage.setUsername(username);
        aiMessage.setMessage(aiResponse);
        aiMessage.setFromAI(true);
        aiMessage.setTimestamp(LocalDateTime.now());
        chattingRepository.save(aiMessage);

        // 응답 반환
        ChatResponseDTO response = new ChatResponseDTO();
        response.setMessage(aiResponse);
        return response;
    }

    @GetMapping("/history")
    public List<ChattingEntity> getChatHistory(HttpServletRequest request) {
        // 쿠키에서 JWT 토큰 추출
        String token = getTokenFromCookies(request);
        if (token == null) {
            throw new RuntimeException("JWT token not found in cookies");
        }

        // JWT에서 username 추출
        String username = jwtUtil.getUsername(token);

        // 유저의 대화 내역 반환
        return chattingRepository.findByUsernameOrderByTimestampAsc(username);
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
