package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.IdentityHashMap;

@Entity
public class Teacher {


    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String egn;


    public Teacher() {

    }

    public School getSchool() {
        return school;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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


