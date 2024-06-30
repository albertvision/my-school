package bg.nbuteam4.myschool.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ADMIN,
    DIRECTOR,
    TEACHER,
    STUDENT,
    PARENT;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
