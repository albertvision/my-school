package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;

@Entity
public class EducObj { //this is subject in school


    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;


    public EducObj() {
    }

    public School getSchool() {
        return school;
    }

    public Long getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }



}


