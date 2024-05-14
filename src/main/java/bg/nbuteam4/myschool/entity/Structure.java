package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

//Structure is "grade" 5a,5b, 12a...
@Entity
public class Structure {

    @Id
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    public Structure() {
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
}
