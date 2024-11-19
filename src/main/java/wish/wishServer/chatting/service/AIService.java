package wish.wishServer.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final RestTemplate restTemplate;

    @Value("${ai.server.url}")
    private String aiServerUrl; // AI 서버 URL

    public String getAIResponse(String username, String message) {
        // 요청 데이터 생성
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("username", username);
        requestPayload.put("message", message);

        // AI 서버에 요청 및 응답 받기
        return restTemplate.postForObject(aiServerUrl + "/get-response", requestPayload, String.class);
    }
}
