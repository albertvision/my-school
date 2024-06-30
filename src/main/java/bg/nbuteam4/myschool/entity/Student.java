package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student",
        uniqueConstraints = @UniqueConstraint(columnNames = {"egn", "school_id"}))
public class Student {

    @Column(length = 10)
    @Id
    private String egn;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(length = 10, nullable = false)
    private String parentEGN;

    private String parentName;
    private int status;

    public Student() {
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentEGN() {
        return parentEGN;
    }

    public void setParentEGN(String parentEGN) {
        this.parentEGN = parentEGN;
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
}

