package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.School;

public class SchoolAverageMark {
    private final School school;
    private final Double averageMark;

    public SchoolAverageMark(School school, Double averageMark) {
        this.school = school;
        this.averageMark = averageMark;
    }

    public School getSchool() {
        return school;
    }

    public Double getAverageMark() {
        return averageMark;
    }
}
