package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "principal")
    private Teacher principal;

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers;


    @OneToMany(mappedBy = "school")
    private Set<SchoolClass> schoolClasses; //grades

    @OneToMany(mappedBy = "school")
    private Set<Student> students;


    @OneToMany(mappedBy = "school")
    private Set<EducObj> educObjects; //subjects

    @OneToMany(mappedBy = "school")
    private Set<TypeMark> typeMarks; //types of grades

    public School() {
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

    public Teacher getPrincipal() {
        return principal;
    }

    public void setPrincipal(Teacher principal) {
        this.principal = principal;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<EducObj> getEducObjects() {
        return educObjects;
    }

    public void setEducObjects(Set<EducObj> educObjects) {
        this.educObjects = educObjects;
    }

    public Set<TypeMark> getTypeMarks() {
        return typeMarks;
    }

    public void setTypeMarks(Set<TypeMark> typeMarks) {
        this.typeMarks = typeMarks;
    }
}
