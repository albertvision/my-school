package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.Role;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.validation.PersonalCode;
import bg.nbuteam4.myschool.validation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class UserSaveRequest {
    @NotEmpty
    @Length(min = 4, max = 32)
    private String username;

    @NotEmpty
    @Length(min = 6)
    private String password;

    @NotEmpty
    @ValidEnum(enumClass = Role.class)
    private String role;

    @NotEmpty
    @PersonalCode
    private String personalCode;

    public UserSaveRequest() {
        username = "";
        password = "";
        role = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public UserSaveRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserSaveRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSaveRequest setRole(String role) {
        this.role = role;
        return this;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public UserSaveRequest setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
        return this;
    }

    public static UserSaveRequest createFromEntity(User user) {
        return new UserSaveRequest()
                .setUsername(user.getUsername())
                .setRole(user.getRole().name())
                .setPassword("")
                .setPersonalCode(user.getPersonalCode());
    }
}
