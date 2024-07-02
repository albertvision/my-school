package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.TypeMarkCreateRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.TypeMark;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.TypeMarkRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/typeMark")
public class TypeMarkController {

    private final TypeMarkRepository typeMarkRepository;
    private final SchoolRepository schoolRepository;

    private final HttpSession httpSession;


    public TypeMarkController(TypeMarkRepository typeMarkRepository, SchoolRepository schoolRepository, HttpSession httpSession) {
        this.typeMarkRepository = typeMarkRepository;
        this.schoolRepository = schoolRepository;
        this.httpSession = httpSession;
    }


    @GetMapping
    public String index(
            Model model,
            @GlobalFilter("school") School school
    ) {
        model.addAttribute("title", "Типове оценки");

        List<TypeMark> typeMarksClass = typeMarkRepository.findBySchoolIdOrderByIdAsc(school.getId());

        model.addAttribute("school", school);
        model.addAttribute("typeMarks", typeMarksClass);
        return "typeMark/index";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name) {
        TypeMark typeMark = typeMarkRepository.findById(id).orElse(null);
        if (typeMark != null) {
            typeMark.setName(name); // Промяна на полето name (или други полета)
            typeMarkRepository.save(typeMark); // Записване на промените
        }
        return "redirect:/typeMark";
    }

    @PostMapping("/create")
    public String createTypeMark(@Valid @ModelAttribute TypeMarkCreateRequest requestTypeMark,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("typeMark", requestTypeMark);

            return "redirect:/typeMark";
        }

        TypeMark typeMark = new TypeMark();
        typeMark.setName(requestTypeMark.getName());
        typeMark.setSchool(schoolRepository.findById(requestTypeMark.getSchoolId()).orElse(null));

        typeMarkRepository.save(typeMark); // Записване на промените

        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));
        return "redirect:/typeMark";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {

        try {
            typeMarkRepository.deleteById(id);
            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            System.out.println("Съществуват свързани данни, изтриването не е възможно.");
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/typeMark");
    }

}
