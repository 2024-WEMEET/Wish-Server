package wish.wishServer.chatting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import wish.wishServer.chatting.entity.ChattingEntity;
import wish.wishServer.chatting.repository.ChattingRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChattingController {

    @Autowired
    private ChattingRepository chattingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/send")
    public ResponseEntity<ChattingEntity> sendMessage(@RequestBody ChattingEntity chatMessage) {
        // 1. Save the user message to the database
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setSender("USER");
        chattingRepository.save(chatMessage);

        // 2. Send the message to the AI server
        String aiServerUrl = "http://ai-server/api/ai/respond";
        ResponseEntity<ChattingEntity> aiResponse = restTemplate.postForEntity(aiServerUrl, chatMessage, ChattingEntity.class);

        // 3. Save the AI response to the database
        ChattingEntity aiMessage = aiResponse.getBody();
        if (aiMessage != null) {
            aiMessage.setUsername(chatMessage.getUsername()); // Set the same username
            aiMessage.setTimestamp(LocalDateTime.now());
            aiMessage.setSender("AI");
            chattingRepository.save(aiMessage);
        }

        // 4. Return the AI response to the frontend
        return ResponseEntity.ok(aiMessage);
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> receiveAIResponse(@RequestBody ChattingEntity aiResponse) {
        // Save the AI response to the database
        aiResponse.setTimestamp(LocalDateTime.now());
        aiResponse.setSender("AI");
        chattingRepository.save(aiResponse);

        // Optionally send acknowledgment
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{username}")
    public List<ChattingEntity> getChatHistory(@PathVariable String username) {
        return chattingRepository.findByUsername(username);
    }
}