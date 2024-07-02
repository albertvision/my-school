package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student",
        uniqueConstraints = @UniqueConstraint(name = "unique_student_egn_per_school", columnNames = {"egn", "school_id"}))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String egn;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(length = 10, nullable = false)
    private String parentEgn;

    private String parentName;
    private int status;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public Student setEgn(String egn) {
        this.egn = egn;
        return this;
    }

    public Student setSchool(School school) {
        this.school = school;
        return this;
    }

    public School getSchool() {
        return school;
    }

    public String getEgn() {
        return egn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNames() {
        return firstName + " " + middleName + " " + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentEgn() {
        return parentEgn;
    }

    public void setParentEgn(String parentEgn) {
        this.parentEgn = parentEgn;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}

