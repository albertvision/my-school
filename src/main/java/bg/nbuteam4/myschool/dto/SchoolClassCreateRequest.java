package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.SchoolClass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class SchoolClassCreateRequest {


    @NotNull
    private Long schoolId;

    @NotEmpty
    @Pattern(regexp = SchoolClass.NAME_REGEX)
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
