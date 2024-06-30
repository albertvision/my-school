package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.enums.AbsenceStatus;
import bg.nbuteam4.myschool.enums.AbsenceType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AbsenceCreateRequest {

    @NotNull
    private Long studyPeriodId;

    @NotNull
    private Long schoolClassId;

    @NotNull
    private Integer studentNumberInClass;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate absenceDate;

    @NotNull
    private Long educObjId;

    @NotNull
    private AbsenceType absenceType;

    @NotNull
    private Long teacherId;

    private String notes;

    private AbsenceStatus status;

    public AbsenceCreateRequest() {
    }

    public Long getStudyPeriodId() {
        return studyPeriodId;
    }

    public void setStudyPeriodId(Long studyPeriodId) {
        this.studyPeriodId = studyPeriodId;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Integer getStudentNumberInClass() {
        return studentNumberInClass;
    }

    public void setStudentNumberInClass(Integer studentNumberInClass) {
        this.studentNumberInClass = studentNumberInClass;
    }

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(LocalDate absenceDate) {
        this.absenceDate = absenceDate;
    }

    public Long getEducObjId() {
        return educObjId;
    }

    public void setEducObjId(Long educObjId) {
        this.educObjId = educObjId;
    }

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AbsenceStatus getStatus() {
        return status;
    }

    public void setStatus(AbsenceStatus status) {
        this.status = status;
    }
}
