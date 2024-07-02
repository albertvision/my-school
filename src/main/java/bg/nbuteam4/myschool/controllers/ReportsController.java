package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.SchoolEducObjAverageMark;
import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.repository.MarkRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/reports")
public class ReportsController {

private final SchoolRepository schoolRepository;
private final MarkRepository markRepository;

    public ReportsController(SchoolRepository schoolRepository, MarkRepository markRepository) {
        this.schoolRepository = schoolRepository;
        this.markRepository = markRepository;
    }


    @GetMapping("/averageSchoolEducObjMarks")
    String averageSchoolEducObjMarks(Model model,
                                     @GlobalFilter("school") School school,
                                     @GlobalFilter("studyPeriod") StudyPeriod studyPeriod
    ) {
        List<School> schools = schoolRepository.findAll();

        Map<EducObj, String> averageSchoolEducObjMarks = markRepository.findAverageSchoolEducObjMarksByStudyPeriodId(school.getId(), studyPeriod.getId())
                .stream()
                .collect(Collectors.toMap(
                        SchoolEducObjAverageMark::getEducObj,
                        it -> Optional.ofNullable(it.getAverageMark()).map(mark -> String.format("%.2f", mark)).orElse("-")
                ));

//        Map<Long, EducObj> schoolEducObjs = educObjRepository.findBySchoolId(school.getId())
//                .stream()
//                .collect(Collectors.toMap(EducObj::getId, Function.identity()));

//        model.addAttribute("schools", schools);
        model.addAttribute("averageSchoolEducObjMarks", averageSchoolEducObjMarks);
        model.addAttribute("title", "Средни оценки на предмет в училище");
//        model.addAttribute("schoolEducObjs", schoolEducObjs);

        return "reports/averageSchoolEducObjMarks";
    }












}
