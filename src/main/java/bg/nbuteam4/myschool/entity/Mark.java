package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mark",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "study_period_id", "school_class_id", "student_number_in_class", "educ_obj_id", "teacher_id"}))
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
    @Min(1)
    @Max(6)
    private double value;
    @ManyToOne
    @JoinColumn(nullable = false)
    private TypeMark markType;

    private int status; //0 - текуща, 1 - срочна, 9 - годишна

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

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public int getStudentNumberInClass() {
        return studentNumberInClass;
    }

    public EducObj getEducObj() {
        return educObj;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TypeMark getMarkType() {
        return markType;
    }

    public void setMarkType(TypeMark markType) {
        this.markType = markType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
