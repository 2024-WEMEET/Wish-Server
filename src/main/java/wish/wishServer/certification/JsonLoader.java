package wish.wishServer.certification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonLoader {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static Map<String, Object> loadJsonFile(String filePath) {
    try {
      return objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {
      });
    } catch (IOException e) {
      throw new RuntimeException("Error reading JSON file: " + filePath, e);
    }
  }
}
