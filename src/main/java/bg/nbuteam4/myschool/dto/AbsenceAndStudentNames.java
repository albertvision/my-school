package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.enums.AbsenceStatus;
import bg.nbuteam4.myschool.enums.AbsenceType;
import jakarta.persistence.*;

import java.time.LocalDate;

public class AbsenceAndStudentNames {


    private Long id;
    private Long studyPeriodId;

    private Long schoolClassId;
    private int studentNumberInClass;

    private LocalDate absenceDate;

    private EducObj educObj;
    private AbsenceType absenceType;

    private Long teacherId;

    private String notes;
    private AbsenceStatus status;

    private String studentNames;

    public AbsenceAndStudentNames() {
    }

    public AbsenceAndStudentNames(Long id, Long studyPeriodId, Long schoolClassId, int studentNumberInClass, LocalDate absenceDate, EducObj educObj, AbsenceType absenceType, Long teacherId, String notes, AbsenceStatus status, String studentNames) {
        this.id = id;
        this.studyPeriodId = studyPeriodId;
        this.schoolClassId = schoolClassId;
        this.studentNumberInClass = studentNumberInClass;
        this.absenceDate = absenceDate;
        this.educObj = educObj;
        this.absenceType = absenceType;
        this.teacherId = teacherId;
        this.notes = notes;
        this.status = status;
        this.studentNames = studentNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(LocalDate absenceDate) {
        this.absenceDate = absenceDate;
    }

    public EducObj getEducObj() {
        return educObj;
    }

    public void setEducObj(EducObj educObj) {
        this.educObj = educObj;
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

    public String getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
    }
}