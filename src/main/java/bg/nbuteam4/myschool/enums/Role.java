package bg.nbuteam4.myschool.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ADMIN("Администратор"),
    DIRECTOR("Директор"),
    TEACHER("Учител"),
    STUDENT("Ученик"),
    PARENT("Родител");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
