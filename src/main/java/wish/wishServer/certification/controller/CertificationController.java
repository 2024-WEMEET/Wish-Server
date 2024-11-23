package wish.wishServer.certification.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wish.wishServer.certification.dto.CertificationDTO;
import wish.wishServer.certification.dto.ExamScheduleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/certifications")
public class CertificationController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<CertificationDTO> getCertifications() {
        List<CertificationDTO> responseList = new ArrayList<>();

        try {
            // Read certification.json
            ClassPathResource certificationResource = new ClassPathResource("certification.json");
            Map<String, Object> certifications = objectMapper.readValue(certificationResource.getInputStream(), Map.class);

            // Read test.json
            ClassPathResource testResource = new ClassPathResource("test.json");
            JsonNode testSchedules = objectMapper.readTree(testResource.getInputStream());

            // Map certification.json data to DTO
            for (String key : certifications.keySet()) {
                Map<String, Object> certData = (Map<String, Object>) certifications.get(key);

                // Iterate over all test entries in test.json
                for (Iterator<String> it = testSchedules.fieldNames(); it.hasNext(); ) {
                    String testName = it.next(); // Get test name dynamically
                    JsonNode testData = testSchedules.get(testName);

                    // Extract fields
                    String middleClassification = (String) certData.get("Middle classification");
                    String majorCategory = (String) certData.get("Major Category");

                    // Build Written Exam Schedule
                    ExamScheduleDTO writtenExam = new ExamScheduleDTO(
                            testData.get("필기 원서접수 시작일").asText() + " ~ " + testData.get("필기 원서접수 마감일").asText(),
                            testData.get("필기시험일자").asText(),
                            testData.get("필기시험 합격 발표일").asText()
                    );

                    // Build Interview Exam Schedule
                    ExamScheduleDTO interviewExam = new ExamScheduleDTO(
                            testData.get("면접시험 원서접수 시작일").asText() + " ~ " + testData.get("면접시험 원서접수 마감일").asText(),
                            testData.get("면접시험 시험 시작일").asText(),
                            testData.get("합격자 발표일").asText()
                    );

                    // Build CertificationResponseDTO
                    CertificationDTO certificationDTO = new CertificationDTO();
                    certificationDTO.setTitle(key); // Only set the certification title, e.g., "가스기술사"
                    certificationDTO.setInfo(middleClassification);
                    certificationDTO.setMajorCategory(majorCategory);
                    certificationDTO.setApplicationStartDate(testData.get("필기 원서접수 시작일").asText());
                    certificationDTO.setDetails(testName); // Use dynamic test name as details
                    certificationDTO.setWrittenExam(writtenExam);
                    certificationDTO.setInterviewExam(interviewExam);

                    // Add to response list
                    responseList.add(certificationDTO);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseList;
    }
}