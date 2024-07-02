package bg.nbuteam4.myschool.entity;

import bg.nbuteam4.myschool.enums.MarkStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mark",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_period_id", "school_class_id", "student_number_in_class", "educ_obj_id", "teacher_id"}))
public class Mark { //it used to be called "Result" but it was too ambiguous

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_period_id", nullable = false)
    private StudyPeriod studyPeriod;

    @ManyToOne
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @Column(name = "student_number_in_class", nullable = false)
    private int studentNumberInClass;

    @ManyToOne
    @JoinColumn(name = "educ_obj_id", nullable = false)
    private EducObj educObj;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
//    @Min(1)
//    @Max(6)
    @Check(constraints = "`value` between 1 and 6")
    private double value;
    @ManyToOne
    @JoinColumn(nullable = false)
    private TypeMark typeMark;

    private MarkStatus status; //0 - текуща, 1 - срочна, 9 - годишна

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    public Mark() {
    }

    public Long getId() {
        return id;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
