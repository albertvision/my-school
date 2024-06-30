package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.Objects;

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

    public void setSchool(School school) {
        this.school = school;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducObj educObj = (EducObj) o;
        return Objects.equals(id, educObj.id) && Objects.equals(school, educObj.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, school);
    }
}


