package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "absence",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_period_id", "school_class_id", "student_number_in_class", "absence_date", "educ_obj_id", "absence_type"}))
public class Absence {

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

    @Column(name = "absence_date", nullable = false)
    private LocalDate absenceDate;

    @ManyToOne
    @JoinColumn(name = "educ_obj_id", nullable = false)
    private EducObj educObj;
    @Column(name = "absence_type", nullable = false)
    private int absenceType;
    /*
    1 болест
    2 домашни причини
    3 закъснение
    9 отсъствие
    */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    private String notes;

    private int status;
    /*
    Статус:
    0 - необработено
    1 - неизвинено
    2 - извинено
    */

    public Absence() {
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

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public EducObj getEducObj() {
        return educObj;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(int absenceType) {
        this.absenceType = absenceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
