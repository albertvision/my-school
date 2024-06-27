package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.entity.Program;
import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.repository.ProgramRepository;
import bg.nbuteam4.myschool.repository.SchoolClassRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/program")
//@PreAuthorize("hasRole('ADMIN')")
public class ProgramController {
    private final ProgramRepository programRepository;
    private final StudyPeriodRepository studyPeriodRepository;
    private final SchoolClassRepository schoolClassRepository;

    public ProgramController(
            ProgramRepository programRepository,
            StudyPeriodRepository studyPeriodRepository,
            SchoolClassRepository schoolClassRepository
    ) {
        this.programRepository = programRepository;
        this.studyPeriodRepository = studyPeriodRepository;
        this.schoolClassRepository = schoolClassRepository;
    }


    @GetMapping
    public String index(Model model,
                        HttpSession session) {
        model.addAttribute("title", "Седмична програма");
        model.addAttribute("studyPeriods", studyPeriodRepository.findAll());
        model.addAttribute("schoolClasses", schoolClassRepository.findAll());
        return "program/index";
    }

    @PostMapping
    public String find(@RequestParam Long studyPeriodId, @RequestParam Long schoolClassId,
                       HttpSession session,
                       Model model ) {
        // Logic to fetch program data based on studyPeriodId and schoolClassId
        List<Program> programCells = programRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriodId, schoolClassId);

        // Add selected SchoolClass and StudyPeriod to model for displaying their names
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId).orElse(null);
        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElse(null);
        model.addAttribute("schoolClass", schoolClass);
        model.addAttribute("studyPeriod", studyPeriod);

        model.addAttribute("programCells", programCells);
        model.addAttribute("title", "Седмична програма");
        model.addAttribute("studyPeriods", studyPeriodRepository.findAll());
        model.addAttribute("schoolClasses", schoolClassRepository.findAll());


        return "program/index";
    }
}
