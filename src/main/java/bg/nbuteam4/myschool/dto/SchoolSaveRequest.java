package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.School;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class SchoolSaveRequest {
    @NotEmpty
    @Length(min = 4, max = 32)
    private String name;

    @NotEmpty
    @Length(min = 3)
    private String address;

    public String getName() {
        return name;
    }

    public SchoolSaveRequest setName(String nam) {
        this.name = nam;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SchoolSaveRequest setAddress(String address) {
        this.address = address;
        return this;
    }

    public static SchoolSaveRequest createFromEntity(School entity) {
        return new SchoolSaveRequest()
                .setName(entity.getName())
                .setAddress(entity.getAddress());
    }

    public School toEntity(School entity) {
        entity.setAddress(getAddress());
        entity.setName(getName());

        return entity;
    }
}
