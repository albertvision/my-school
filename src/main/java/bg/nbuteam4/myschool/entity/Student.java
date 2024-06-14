package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    private String egn;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(nullable = false)
    private String parentEGN;

    private String parentName;
    private int status;

    public Student() {
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

