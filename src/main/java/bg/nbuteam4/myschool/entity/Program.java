package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "program",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_period_id", "school_class_id", "day_of_week", "lesson_order"}))
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "study_period_id", nullable = false)
    private StudyPeriod studyPeriod;
    @ManyToOne
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass; //this is the school class itself e.g 5a
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;
    @Column(name = "lesson_order", nullable = false)
    private int lessonOrder;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EducObj educObj;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    public Program() {
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getLessonOrder() {
        return lessonOrder;
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
}
