package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.Min;

public class GlobalFiltersRequest {

    @Min(1)
    private Long schoolId;

    private Long studyPeriodId;

    public GlobalFiltersRequest() {

    }

    public Long getSchoolId() {
        return schoolId;
    }

    public Long getStudyPeriodId() {
        return studyPeriodId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public void setStudyPeriodId(Long studyPeriodId) {
        this.studyPeriodId = studyPeriodId;
    }
}
