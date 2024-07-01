package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.TypeMark;
import bg.nbuteam4.myschool.enums.MarkStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarkAndStudentNames {

    private Long id;

    private Long studyPeriodId;
    private Long schoolClassId;
    private int studentNumberInClass;

    private EducObj educObj;

    private Long teacherId;
    private double value;
    private TypeMark typeMark;

    private MarkStatus status;

    private LocalDateTime dateTime;

    private String studentNames;

    public MarkAndStudentNames() {
    }

    public MarkAndStudentNames(Long id, Long studyPeriodId, Long schoolClassId, int studentNumberInClass, EducObj educObj, Long teacherId, double value, TypeMark typeMark, MarkStatus status, LocalDateTime dateTime, String studentNames) {
        this.id = id;
        this.studyPeriodId = studyPeriodId;
        this.schoolClassId = schoolClassId;
        this.studentNumberInClass = studentNumberInClass;
        this.educObj = educObj;
        this.teacherId = teacherId;
        this.value = value;
        this.typeMark = typeMark;
        this.status = status;
        this.dateTime = dateTime;
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

    public EducObj getEducObj() {
        return educObj;
    }

    public void setEducObj(EducObj educObj) {
        this.educObj = educObj;
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

    public String getDateTimeFormatted() {
        LocalDateTime dt = dateTime;

        return dt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
    }
}