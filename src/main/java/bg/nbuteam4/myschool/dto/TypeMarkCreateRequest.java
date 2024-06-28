package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotNull;


public class TypeMarkCreateRequest {


    @NotNull
    private Long schoolId;

    private String name;

    public TypeMarkCreateRequest() {
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
