package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

//SchoolClass is "grade" 5a,5b, 12a...
@Entity
@Table(name = "school_class",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "name"}))

public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Column(name = "name", nullable = false)
    private String name;

    public SchoolClass() {
    }

    public Long getId() {
        return id;
    }
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
