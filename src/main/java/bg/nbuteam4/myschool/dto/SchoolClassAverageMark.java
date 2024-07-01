package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.SchoolClass;

public class SchoolClassAverageMark {
    private final SchoolClass schoolClass;
    private final Double averageMark;

    public SchoolClassAverageMark(SchoolClass schoolClass, Double averageMark) {
        this.schoolClass = schoolClass;
        this.averageMark = averageMark;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public Double getAverageMark() {
        return averageMark;
    }
}
