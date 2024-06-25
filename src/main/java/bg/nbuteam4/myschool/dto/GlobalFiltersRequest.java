package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotNull;

public class GlobalFiltersRequest {

    @NotNull
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
}
