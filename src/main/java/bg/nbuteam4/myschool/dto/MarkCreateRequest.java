package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.enums.MarkStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

public class MarkCreateRequest {

    @NotNull
    private Long studyPeriodId;

    @NotNull
    private Long schoolClassId;

    @NotNull
    private int studentNumberInClass;

    @NotNull
    private Long educObjId;

    @NotNull
    private Long teacherId;
    //    @Min(1)
//    @Max(6)
    @Min(2)
    @Max(6)
    private double value;
    @NotNull
    private TypeMark typeMark;

    private MarkStatus status; //0 - текуща, 1 - срочна, 9 - годишна

    @NotNull
    private LocalDateTime dateTime;

    public MarkCreateRequest() {
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

    public int getStudentNumberInClass() {
        return studentNumberInClass;
    }

    public void setStudentNumberInClass(int studentNumberInClass) {
        this.studentNumberInClass = studentNumberInClass;
    }

    public Long getEducObjId() {
        return educObjId;
    }

    public void setEducObjId(Long educObjId) {
        this.educObjId = educObjId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TypeMark getTypeMark() {
        return typeMark;
    }

    public void setTypeMark(TypeMark typeMark) {
        this.typeMark = typeMark;
    }

    public MarkStatus getStatus() {
        return status;
    }

    public void setStatus(MarkStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
