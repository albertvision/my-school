package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
//@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    //I spent some time pondering on this and I think there shouldn't be fields CurrentYear and CurrentSemester in School (they are already present in StudyPeriod)


    //should this be a OneToOne School-Teacher(director)? Because the director is one of the teachers
//    @OneToOne
//    @JoinColumn
    private Teacher principal;

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers;


    @OneToMany(mappedBy = "school")
    private Set<Structure> structures; //grades

    @OneToMany(mappedBy = "school")
    private Set<Student> students;


    @OneToMany(mappedBy = "school")
    private Set<EduObject> eduObjects; //subjects

    @OneToMany(mappedBy = "school")
    private Set<TypeResult> typeResults; //types of grades



}
