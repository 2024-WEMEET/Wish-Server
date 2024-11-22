package wish.wishServer.certification.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;

public class JsonLoader {

  public static Map<String, Object> loadJsonFile(String fileName) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      // 리소스 경로에서 파일 읽기
      return objectMapper.readValue(new ClassPathResource(fileName).getInputStream(), Map.class);
    } catch (IOException e) {
      // 파일 로드 실패 시 예외 발생
      throw new RuntimeException("Failed to load JSON file: " + fileName, e);
    }
  }
}