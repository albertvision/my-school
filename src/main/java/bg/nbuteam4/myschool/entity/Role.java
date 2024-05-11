package bg.nbuteam4.myschool.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ADMIN,
    DIRECTOR,
    TEACHER,
    STUDENT,
    PARENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
