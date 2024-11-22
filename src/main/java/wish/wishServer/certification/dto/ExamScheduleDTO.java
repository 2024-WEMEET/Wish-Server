package wish.wishServer.certification.dto;

public class ExamScheduleDTO {

    private String applicationPeriod; // 원서접수 기간
    private String examDate; // 시험일자
    private String resultDate; // 합격 발표일

    // 기본 생성자
    public ExamScheduleDTO() {}

    // 모든 필드를 포함한 생성자
    public ExamScheduleDTO(String applicationPeriod, String examDate, String resultDate) {
        this.applicationPeriod = applicationPeriod;
        this.examDate = examDate;
        this.resultDate = resultDate;
    }

    // Getter & Setter
    public String getApplicationPeriod() {
        return applicationPeriod;
    }

    public void setApplicationPeriod(String applicationPeriod) {
        this.applicationPeriod = applicationPeriod;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }
}
