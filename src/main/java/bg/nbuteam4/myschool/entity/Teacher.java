package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.IdentityHashMap;

@Entity
@Table(name = "teacher",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "school_id"}))
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private String name;
    @Column(length = 10, nullable = false)
    private String egn;


    public Teacher() {

    }

    public Long getId() {
        return id;
    }
    public School getSchool() {
        return school;
    }
    public String getName() {
        return name;
    }

    public Teacher setSchool(School school) {
        this.school = school;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }
}


