package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;
import org.yaml.snakeyaml.events.Event;

@Entity
public class StudyPeriod {

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int currentYear;
    @Column(nullable = false)
    private int currentSemester;


    private String name;

    private int status;

    public StudyPeriod() {
    }

    public School getSchool() {
        return school;
    }

    public Long getId() {
        return id;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
