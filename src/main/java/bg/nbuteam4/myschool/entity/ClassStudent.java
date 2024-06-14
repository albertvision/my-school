package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class_student",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_period_id", "school_class_id", "student_EGN", "student_number_in_class"}))
public class ClassStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_period_id", nullable = false)
    private StudyPeriod studyPeriod;

    @ManyToOne
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne //the following year the same student will have a different number
    @JoinColumn(name = "student_EGN", nullable = false)
    private Student student;

    @Column(nullable = false)
    private int studentNumberInClass;

    public ClassStudent() {
    }

    public Long getId() {
        return id;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public SchoolClass getStructure() {
        return schoolClass;
    }

    public Student getStudent() {
        return student;
    }

    public int getStudentNumberInClass() {
        return studentNumberInClass;
    }


    public void setStudentNumberInClass(int studentNumberInClass) {
        this.studentNumberInClass = studentNumberInClass;
    }

}
