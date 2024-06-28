package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotNull;


public class TeacherCreateRequest {


    @NotNull
    private Long schoolId;

    private String name;

    private String egn;


    public TeacherCreateRequest() {
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

    public String getEgn() {
        return egn;
    }

    public TeacherCreateRequest setEgn(String egn) {
        this.egn = egn;
        return this;
    }
}
