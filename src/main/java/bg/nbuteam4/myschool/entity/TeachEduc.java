package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teach_educ", //a relationship table
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "teacher_id", "educ_obj_id"}))
public class TeachEduc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "educ_obj_id", nullable = false)
    private EducObj educObj;

    public TeachEduc() {
    }

    public Long getId() {
        return id;
    }

    public School getSchool() {
        return school;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public EducObj getEducObj() {
        return educObj;
    }

    public TeachEduc setSchool(School school) {
        this.school = school;
        return this;
    }

    public TeachEduc setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public TeachEduc setEducObj(EducObj educObj) {
        this.educObj = educObj;

        return this;
    }
}
