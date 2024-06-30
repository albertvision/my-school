package bg.nbuteam4.myschool.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class StudentSaveRequest {
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

    private int status;


    public StudentSaveRequest() {
        egn = "";
        firstName = "";
        middleName = "";
        lastName = "";
        parentEgn = "";
        parentName = "";
        status = 0;
    }

    public @NotEmpty @Length(min = 10, max = 10) String getEgn() {
        return egn;
    }

    public StudentSaveRequest setEgn(@NotEmpty @Length(min = 10, max = 10) String egn) {
        this.egn = egn;
        return this;
    }

    public @NotEmpty String getFirstName() {
        return firstName;
    }

    public StudentSaveRequest setFirstName(@NotEmpty String firstName) {
        this.firstName = firstName;
        return this;
    }

    public @NotEmpty String getMiddleName() {
        return middleName;
    }

    public StudentSaveRequest setMiddleName(@NotEmpty String middleName) {
        this.middleName = middleName;
        return this;
    }

    public @NotEmpty String getLastName() {
        return lastName;
    }

    public StudentSaveRequest setLastName(@NotEmpty String lastName) {
        this.lastName = lastName;
        return this;
    }

    public @NotEmpty @Length(min = 10, max = 10) String getParentEgn() {
        return parentEgn;
    }

    public StudentSaveRequest setParentEgn(@NotEmpty @Length(min = 10, max = 10) String parentEgn) {
        this.parentEgn = parentEgn;
        return this;
    }

    public @NotEmpty String getParentName() {
        return parentName;
    }

    public StudentSaveRequest setParentName(@NotEmpty String parentName) {
        this.parentName = parentName;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public StudentSaveRequest setStatus(int status) {
        this.status = status;
        return this;
    }
}
