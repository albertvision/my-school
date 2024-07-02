package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.SchoolEducObjAverageMark;
import bg.nbuteam4.myschool.dto.SchoolSaveRequest;
import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import bg.nbuteam4.myschool.repository.MarkRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.TeacherRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/schools")
@PreAuthorize("hasRole('ADMIN')")
public class SchoolController {
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final MarkRepository markRepository;
    private final EducObjRepository educObjRepository;

    public SchoolController(
            SchoolRepository schoolRepository,
            TeacherRepository teacherRepository,
            MarkRepository markRepository, EducObjRepository educObjRepository
    ) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.markRepository = markRepository;
        this.educObjRepository = educObjRepository;
    }

    @GetMapping
    String index(Model model) {
        List<School> schools = schoolRepository.findAll();

        Map<Long, String> averageSchoolMarks = markRepository.findAverageSchoolMarks()
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getSchool().getId(),
                        it -> Optional.ofNullable(it.getAverageMark()).map(mark -> String.format("%.2f", mark)).orElse("-")
                ));

        model.addAttribute("schools", schools);
        model.addAttribute("averageSchoolMarks", averageSchoolMarks);
        model.addAttribute("title", "Училища");

        return "schools/index";
    }




    @GetMapping("/{id}")
    String edit(
            Model model,
            @PathVariable("id") Long id
    ) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        SchoolSaveRequest request = SchoolSaveRequest.createFromEntity(school);

        model.addAttribute("request", request);
        model.addAttribute("title", "Редактиране на училище");

        return "schools/save";
    }

    @PostMapping("/{id}")
    String doEdit(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute SchoolSaveRequest request,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("request", request);
        } else {
            request.toEntity(school);

            schoolRepository.save(school);
            attributes.addFlashAttribute("result", new ActionResult("Успешно обновяване.", ActionResultType.SUCCESS));
        }

        return "redirect:/schools/" + id;
    }

    @GetMapping("/create")
    String create(Model model) {
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new SchoolSaveRequest());
        }

        model.addAttribute("title", "Ново училище");

        return "schools/save";
    }

    @PostMapping("/create")
    String doCreate(@Valid @ModelAttribute SchoolSaveRequest request,
                    BindingResult result,
                    RedirectAttributes attributes
    ) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("request", request);

            return "redirect:/schools/create";
        }

        School entity = new School();
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());

        // todo take care of when username is taken
        schoolRepository.save(entity);

        attributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));

        return "redirect:/schools";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {
        schoolRepository.deleteById(id);

        attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));

        return new RedirectView("/schools");
    }
}
