package wish.wishServer.certification.dto;

public class CertificationDTO {

    private String title; // 기술사 이름 (예: "가스기술사")
    private String info; // 자격증 정보 (예: "안전관리")
    private String majorCategory; // 대분류 (예: "안전관리")
    private String applicationStartDate; // 접수 시작일 (예: "2024.01.21")
    private String details; // 세부 일정 (예: "2024년도 제132회")

    private ExamScheduleDTO writtenExam; // 필기시험 정보
    private ExamScheduleDTO interviewExam; // 면접시험 정보

    // 기본 생성자
    public CertificationDTO() {}

    // 모든 필드를 포함한 생성자
    public CertificationDTO(String title, String info, String majorCategory, String applicationStartDate,
                            String details, ExamScheduleDTO writtenExam, ExamScheduleDTO interviewExam) {
        this.title = title;
        this.info = info;
        this.majorCategory = majorCategory;
        this.applicationStartDate = applicationStartDate;
        this.details = details;
        this.writtenExam = writtenExam;
        this.interviewExam = interviewExam;
    }

    // Getter & Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMajorCategory() {
        return majorCategory;
    }

    public void setMajorCategory(String majorCategory) {
        this.majorCategory = majorCategory;
    }

    public String getApplicationStartDate() {
        return applicationStartDate;
    }

    public void setApplicationStartDate(String applicationStartDate) {
        this.applicationStartDate = applicationStartDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ExamScheduleDTO getWrittenExam() {
        return writtenExam;
    }

    public void setWrittenExam(ExamScheduleDTO writtenExam) {
        this.writtenExam = writtenExam;
    }

    public ExamScheduleDTO getInterviewExam() {
        return interviewExam;
    }

    public void setInterviewExam(ExamScheduleDTO interviewExam) {
        this.interviewExam = interviewExam;
    }
}
