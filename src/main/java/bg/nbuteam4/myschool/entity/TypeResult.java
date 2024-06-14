package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
public class TypeResult {


    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    private int resultType;
    /*TypeResult
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

    private String name;

    public TypeResult() {
    }

    public School getSchool() {
        return school;
    }

    public int getResultType() {
        return resultType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
