package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.Student;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class StudentSaveRequest {
    private Long id;

    @NotEmpty
    @Length(min = 10, max = 10)
    private String egn;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String middleName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Length(min = 10, max = 10)
    private String parentEgn;

    @NotEmpty
    private String parentName;

    private boolean status;

    public String getEgn() {
        return egn;
    }

    public StudentSaveRequest setEgn(@NotEmpty @Length(min = 10, max = 10) String egn) {
        this.egn = egn;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public StudentSaveRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public StudentSaveRequest setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public StudentSaveRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getParentEgn() {
        return parentEgn;
    }

    public StudentSaveRequest setParentEgn(String parentEgn) {
        this.parentEgn = parentEgn;
        return this;
    }

    public String getParentName() {
        return parentName;
    }

    public StudentSaveRequest setParentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public StudentSaveRequest setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Student toEntity(Student student) {
        student.setEgn(getEgn());
        student.setFirstName(getFirstName());
        student.setMiddleName(getMiddleName());
        student.setLastName(getLastName());
        student.setParentEgn(getParentEgn());
        student.setParentName(getParentName());
        student.setStatus(getStatus() ? 1 : 0); // todo

        return student;
    }

    public static StudentSaveRequest createFromEntity(Student student) {
        return new StudentSaveRequest()
                .setId(student.getId())
                .setEgn(student.getEgn())
                .setFirstName(student.getFirstName())
                .setMiddleName(student.getMiddleName())
                .setLastName(student.getLastName())
                .setParentEgn(student.getParentEgn())
                .setParentName(student.getParentName())
                .setStatus(student.getStatus() == 1);
    }

    public Long getId() {
        return id;
    }

    public StudentSaveRequest setId(Long id) {
        this.id = id;
        return this;
    }
}
