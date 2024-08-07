package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.SchoolClassCreateRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.MarkRepository;
import bg.nbuteam4.myschool.repository.SchoolClassRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schoolClass")
@PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'TEACHER')")
public class SchoolClassController {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;
    private final MarkRepository markRepository;


    public SchoolClassController(SchoolClassRepository schoolClassRepository, SchoolRepository schoolRepository, MarkRepository markRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.schoolRepository = schoolRepository;
        this.markRepository = markRepository;
    }


    @GetMapping
    public String index(
            Model model,
            @GlobalFilter(value = "school") School school,
            @GlobalFilter(value = "studyPeriod", required = false) StudyPeriod studyPeriod
    ) {
        List<SchoolClass> schoolschoolClass = schoolClassRepository.findBySchoolId(school.getId())
                .stream()
                .sorted()
                .toList();

        Map<Long, String> averageSchoolClassMarks = Optional.ofNullable(studyPeriod)
                .map(it -> markRepository.findAverageSchoolClassMarksByStudyPeriodId(it.getId()))
                .orElseGet(markRepository::findAverageSchoolClassMarks)
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getSchoolClass().getId(),
                        it -> Optional.ofNullable(it.getAverageMark()).map(mark -> String.format("%.2f", mark)).orElse("-")
                ));

        model.addAttribute("title", "Класове");
        model.addAttribute("school", school);
        model.addAttribute("schoolClass", schoolschoolClass);
        model.addAttribute("averageSchoolClassMarks", averageSchoolClassMarks);

        return "schoolClass/index";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name, Model model) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);
        if (schoolClass != null) {
            schoolClass.setName(name); // Промяна на полето name (или други полета)
            schoolClassRepository.save(schoolClass); // Записване на промените
        }
        return "redirect:/schoolClass";
    }

    @PostMapping("/create")
    public String createSchoolClass(@Valid @ModelAttribute SchoolClassCreateRequest requestschoolClass,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("schoolClass", requestschoolClass);

            return "redirect:/schoolClass";
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(requestschoolClass.getName());
        schoolClass.setSchool(schoolRepository.findById(requestschoolClass.getSchoolId()).orElse(null));

        schoolClassRepository.save(schoolClass); // Записване на промените

        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));
        return "redirect:/schoolClass";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {

        try {
            schoolClassRepository.deleteById(id);
            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            System.out.println("Съществуват свързани данни, изтриването не е възможно.");
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/schoolClass");
    }

}
