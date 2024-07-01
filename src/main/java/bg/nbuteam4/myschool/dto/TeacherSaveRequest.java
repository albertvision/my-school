package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.TeachEduc;
import bg.nbuteam4.myschool.entity.Teacher;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;


public class TeacherSaveRequest {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String egn;

    private Boolean principal;

    private List<Long> subjectIds = new ArrayList<>();

    public static TeacherSaveRequest create(
            Teacher teacher,
            School school,
            List<TeachEduc> teacherSubjects
    ) {
        return new TeacherSaveRequest()
                .setId(teacher.getId())
                .setName(teacher.getName())
                .setEgn(teacher.getEgn())
                .setPrincipal(school.getPrincipal() != null && school.getPrincipal().equals(teacher))
                .setSubjectIds(teacherSubjects.stream().map(it -> it.getEducObj().getId()).toList());
    }

    public String getName() {
        return name;
    }

    public TeacherSaveRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getEgn() {
        return egn;
    }

    public TeacherSaveRequest setEgn(String egn) {
        this.egn = egn;
        return this;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public TeacherSaveRequest setPrincipal(Boolean principal) {
        this.principal = principal;
        return this;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public TeacherSaveRequest setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
        return this;
    }

    public Long getId() {
        return id;
    }

    public TeacherSaveRequest setId(Long id) {
        this.id = id;
        return this;
    }
}
