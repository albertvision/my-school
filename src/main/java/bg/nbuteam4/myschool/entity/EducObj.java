package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "educ_obj",
uniqueConstraints = @UniqueConstraint(columnNames = {"id", "school_id"}))
public class EducObj { //this is subject in school

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name ="school_id", nullable = false)
    private School school;

    private String name;

    public EducObj() {
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


