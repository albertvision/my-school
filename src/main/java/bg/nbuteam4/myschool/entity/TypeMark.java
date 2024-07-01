package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mark",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "name"}))
public class TypeMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //could be enum as well? Primary key might not work for different schools. Can be changed to only id, school and enum columns, I guess.
    /*TypeMark
    0   Устно изпитване
    1   Активно участие
    2   Контролна работа
    3   Тест
    4   Самостоятелна работа
    5   Практическо изпитване
    6   Проект
    7   Домашна работа
    8   Входно ниво
    9   Изходно ниво
    10 Класна работа
    */
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private String name;

    public TypeMark() {
    }

    public School getSchool() {
        return school;
    }

    public TypeMark setSchool(School school) {
        this.school = school;
        return this;
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
