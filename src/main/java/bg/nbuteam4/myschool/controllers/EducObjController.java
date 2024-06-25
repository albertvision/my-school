package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.dto.EducObjCreateRequest;
import bg.nbuteam4.myschool.dto.UserCreateRequest;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

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
    public String index(Model model) {
        model.addAttribute("title", "Преподавани предмети");

//        model.addAttribute("schools", schoolRepository.findAll());
        School school = schoolRepository.findById(1L).orElse(null);
        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(1L);

        model.addAttribute("school", school);

        model.addAttribute("educObjs", schoolEducObjs);
        model.addAttribute("title", "Преподавани предмети");
//        model.addAttribute("schools", schoolRepository.findAll());


        return "educObjects/index";
    }
//TODO change these so that the school is taken from session
    //old
    /* @PostMapping
    public String find(@RequestParam Long schoolId, Model model) {
        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(schoolId);

        School school = schoolRepository.findById(schoolId).orElse(null);
        model.addAttribute("school", school);

        model.addAttribute("educObjs", schoolEducObjs);
        model.addAttribute("title", "Преподавани предмети");
//        model.addAttribute("schools", schoolRepository.findAll());

        return "educObjects/index";
    }*/


//for editing fields

//    @PostMapping("/{id}/update")
//    @ResponseBody
//    public ResponseEntity<?> updateEducObj(@PathVariable Long id, @RequestBody EducObj newEducObj) {
//        return educObjRepository.findById(id)
//                .map(educObj -> {
//                    educObj.setName(newEducObj.getName());
//                    educObjRepository.save(educObj);
//                    return ResponseEntity.ok().build();
//                }).orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name, Model model) {
        EducObj educObj = educObjRepository.findById(id).orElse(null);
        if (educObj != null) {
            educObj.setName(name); // Промяна на полето name (или други полета)
            educObjRepository.save(educObj); // Записване на промените
        }
        return "redirect:/educObjects"; // Пренасочване обратно към списъка с образователни обекти
    }


//    @PutMapping("/{id}/update")
//    @ResponseBody
//    public ResponseEntity<?> updateEducObj(@PathVariable Long id, @RequestBody String name) {
//        return educObjRepository.findById(id)
//                .map(educObj -> {
//                    educObj.setName(name);
//                    educObjRepository.save(educObj);
//                    return ResponseEntity.ok().build();
//                }).orElse(ResponseEntity.notFound().build());
//    }
//


//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("title", "Добавяне на нов Предмет");
//        model.addAttribute("educObj", new EducObj());
//        model.addAttribute("schools", schoolRepository.findAll());
//        return "educObjects/create"; //
//    }

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
