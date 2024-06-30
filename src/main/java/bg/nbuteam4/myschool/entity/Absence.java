package bg.nbuteam4.myschool.entity;

import bg.nbuteam4.myschool.enums.AbsenceStatus;
import bg.nbuteam4.myschool.enums.AbsenceType;
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
    @Temporal(TemporalType.DATE)
    private LocalDate absenceDate;

    @ManyToOne
    @JoinColumn(name = "educ_obj_id", nullable = false)
    private EducObj educObj;
    @Column(name = "absence_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AbsenceType absenceType;
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
    @Enumerated(EnumType.STRING)
    private AbsenceStatus status;
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

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
