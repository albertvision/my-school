package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.Role;
import bg.nbuteam4.myschool.validation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserCreateRequest {
    @NotEmpty
    @Length(min = 4, max = 32)
    private String username;

    @NotEmpty
    @Length(min = 6)
    private String password;

    @NotNull
    @ValidEnum(enumClass = Role.class)
    private String role;

    public UserCreateRequest() {
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

    public UserCreateRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserCreateRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserCreateRequest setRole(String role) {
        this.role = role;
        return this;
    }
}
