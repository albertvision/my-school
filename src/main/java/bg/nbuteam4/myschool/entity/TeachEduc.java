package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TeachEduc", //a relationship table
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
}
