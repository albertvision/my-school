package bg.nbuteam4.myschool.entity;

import jakarta.persistence.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//SchoolClass is "grade" 5a,5b, 12a...
@Entity
@Table(name = "school_class",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "name"}))

public class SchoolClass implements Comparable<SchoolClass> {

    public static final String NAME_REGEX = "^([1-9][0-2]?)([а-я])$";
    public static final Pattern CLASS_NAME_PATTERN = Pattern.compile(NAME_REGEX);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "name", nullable = false)
    private String name;

    public SchoolClass() {
    }

    public Long getId() {
        return id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClassIndex() {
        Matcher m = CLASS_NAME_PATTERN.matcher(getName());

        if (m.find()) {
            return Integer.valueOf(m.group(1));
        }

        throw new RuntimeException("Invalid class index");
    }

    public String getClassSubname() {
        Matcher m = CLASS_NAME_PATTERN.matcher(getName());

        if (m.find()) {
            return m.group(2);
        }

        throw new RuntimeException("Invalid class subname");
    }

    @Override
    public int compareTo(SchoolClass o) {
        int result = getClassIndex().compareTo(o.getClassIndex());

        if (result != 0) {
            return result;
        }

        return getClassSubname().compareTo(o.getClassSubname());
    }
}
