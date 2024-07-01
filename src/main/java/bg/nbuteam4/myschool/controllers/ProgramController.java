package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.entity.Program;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.exception.InvalidGlobalFilterException;
import bg.nbuteam4.myschool.repository.ProgramRepository;
import bg.nbuteam4.myschool.repository.SchoolClassRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/program")
//@PreAuthorize("hasRole('ADMIN')")
public class ProgramController {
    private final ProgramRepository programRepository;
    private final StudyPeriodRepository studyPeriodRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;

    public ProgramController(
            ProgramRepository programRepository,
            StudyPeriodRepository studyPeriodRepository,
            SchoolClassRepository schoolClassRepository,
            SchoolRepository schoolRepository) {
        this.programRepository = programRepository;
        this.studyPeriodRepository = studyPeriodRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.schoolRepository = schoolRepository;
    }


    @GetMapping
    public String index(
            Model model,
            @SessionAttribute("schoolId") Long schoolId,
            @SessionAttribute("studyPeriodId") Long studyPeriodId,
            @RequestParam("schoolClassId") Optional<Long> schoolClassId,
            RedirectAttributes redirectAttributes
    ) {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new InvalidGlobalFilterException("Невалидно училище."));
        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElseThrow(() -> new InvalidGlobalFilterException("Невалиден срок."));

        if (schoolClassId.isPresent()) {
            SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId.get()).orElse(null);

            if (schoolClass == null || !schoolClass.getSchool().equals(school)) {
                redirectAttributes.addFlashAttribute("result", new ActionResult("Класът не е намерен в това учлиище.", ActionResultType.ERROR));

                return "redirect:/program";
            }

            List<Program> programCells = programRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriodId, schoolClass.getId());

            model.addAttribute("schoolClass", schoolClass);
            model.addAttribute("programCells", programCells);
            model.addAttribute("daysOfWeek", Arrays.stream(DayOfWeek.values()).filter(it -> it.getValue() < 6).toList());
        }

        model.addAttribute("studyPeriod", studyPeriod);
        model.addAttribute("title", "Седмична програма");
        model.addAttribute("schoolClasses", schoolClassRepository.findBySchoolId(school.getId()).stream().sorted().toList());

        return "program/index";
    }
}
