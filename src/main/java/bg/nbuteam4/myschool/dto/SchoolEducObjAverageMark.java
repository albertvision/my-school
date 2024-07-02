package bg.nbuteam4.myschool.dto;

import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.SchoolClass;

public class SchoolEducObjAverageMark {
    private final EducObj educObj;
    private final Double averageMark;

    public SchoolEducObjAverageMark(EducObj educObj, Double averageMark) {
        this.educObj = educObj;
        this.averageMark = averageMark;
    }

    public EducObj getEducObj() {
        return educObj;
    }

    public Double getAverageMark() {
        return averageMark;
    }
}
