package wish.wishServer.certification;

import java.util.Map;

public class CertificationService {

  private final Map<String, Object> certificationData;
  private final Map<String, Object> ExamData;

  public CertificationService() {
    // JSON 파일 경로 설정
    this.certificationData = JsonLoader.loadJsonFile("src//main//resource//certification_inform.json");
    this.ExamData = JsonLoader.loadJsonFile("src//main//resource//test_inform.json");
  }

  public Map<String, Object> getCertificationData() {
    return certificationData;
  }

  public Map<String, Object> getExamData() {
    return ExamData;
  }

}
