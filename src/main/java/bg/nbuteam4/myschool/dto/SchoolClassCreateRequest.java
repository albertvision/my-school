package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class SchoolClassCreateRequest {


    @NotNull
    private Long schoolId;

    private String name;

    public SchoolClassCreateRequest() {
    }


    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
