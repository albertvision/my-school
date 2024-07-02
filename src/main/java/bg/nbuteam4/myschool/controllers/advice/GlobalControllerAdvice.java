package bg.nbuteam4.myschool.controllers.advice;

import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final SchoolRepository schoolRepository;
    private final StudyPeriodRepository studyPeriodRepository;

    public GlobalControllerAdvice(SchoolRepository schoolRepository, StudyPeriodRepository studyPeriodRepository) {
        this.schoolRepository = schoolRepository;
        this.studyPeriodRepository = studyPeriodRepository;
    }

    @ModelAttribute
    public void addGlobalAttributes(
            Model model,
            @SessionAttribute("schoolId") Optional<Long> schoolId
    ) {
        schoolId.ifPresent(aLong -> model.addAttribute(
                "studyPeriods",
                studyPeriodRepository.findBySchoolIdAndStatusEquals(aLong, 1)
        ));

        model.addAttribute("schools", schoolRepository.findAll());
    }
}
