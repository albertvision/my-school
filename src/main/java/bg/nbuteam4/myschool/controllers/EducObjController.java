package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.EducObjCreateRequest;
import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
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
@RequestMapping("/educObjects")
public class EducObjController {

    private final EducObjRepository educObjRepository;
    private final SchoolRepository schoolRepository;

    public EducObjController(EducObjRepository educObjRepository, SchoolRepository schoolRepository) {
        this.educObjRepository = educObjRepository;
        this.schoolRepository = schoolRepository;
    }


    @GetMapping
    public String index(
            Model model,
            @GlobalFilter("school") School school
    ) {
        model.addAttribute("title", "Преподавани предмети");

        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(school.getId());

        model.addAttribute("school", school);
        model.addAttribute("educObjs", schoolEducObjs);
        model.addAttribute("title", "Преподавани предмети");

        return "educObjects/index";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name) {
        EducObj educObj = educObjRepository.findById(id).orElse(null);
        if (educObj != null) {
            educObj.setName(name); // Промяна на полето name (или други полета)
            educObjRepository.save(educObj); // Записване на промените
        }
        return "redirect:/educObjects"; // Пренасочване обратно към списъка с образователни обекти
    }

    @PostMapping("/create")
    public String createEducObj(@Valid @ModelAttribute EducObjCreateRequest requestEducObj,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("educObj", requestEducObj);

            return "redirect:/educObjects";
        }

        EducObj educObj = new EducObj();
        educObj.setName(requestEducObj.getName());
        educObj.setSchool(schoolRepository.findById(requestEducObj.getSchoolId()).orElse(null));

        educObjRepository.save(educObj); // Записване на промените
        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));
        return "redirect:/educObjects";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {

        try {
            educObjRepository.deleteById(id);
            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            System.out.println("Съществуват свързани данни, изтриването не е възможно.");
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/educObjects");
    }


}
