package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotNull;

public class TeachEducCreateRequest {

    @NotNull(message = "School ID is required")
    private Long schoolId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

//    @NotNull(message = "EducObj ID is required")
    private Long educObjId;

    public TeachEducCreateRequest() {
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getEducObjId() {
        return educObjId;
    }

    public void setEducObjId(Long educObjId) {
        this.educObjId = educObjId;
    }
}
