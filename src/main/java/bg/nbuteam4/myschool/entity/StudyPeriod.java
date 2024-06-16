package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;
import org.yaml.snakeyaml.events.Event;

import java.time.Year;

@Entity
@Table(name = "study_period",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "current_year", "current_semester"}))
public class StudyPeriod {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @Column(name = "current_year", nullable = false)
    private Year currentYear;
    @Column(name = "current_semester", nullable = false)
    private int currentSemester;
    private String name;
    @Column(unique = true)
    private int status;

    public StudyPeriod() {
    }

    public School getSchool() {
        return school;
    }

    public Long getId() {
        return id;
    }

    public Year getCurrentYear() {
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
