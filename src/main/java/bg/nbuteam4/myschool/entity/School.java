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


    @OneToOne
    @JoinColumn
    private Teacher principal;

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers;


    @OneToMany(mappedBy = "school")
    private Set<Structure> structures; //grades

    @OneToMany(mappedBy = "school")
    private Set<Student> students;


    @OneToMany(mappedBy = "school")
    private Set<EducObj> educObjects; //subjects

    @OneToMany(mappedBy = "school")
    private Set<TypeResult> typeResults; //types of grades



}
