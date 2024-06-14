package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

@Entity
public class TeachEduc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private EducObj educObj;
}
