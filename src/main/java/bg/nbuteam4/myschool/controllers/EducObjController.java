package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String index(Model model) {
        model.addAttribute("title", "Преподавани предмети");
//        model.addAttribute("educObjs", educObjRepository.findAll());
        model.addAttribute("schools", schoolRepository.findAll());
        return "educObjects/index";
    }

    @PostMapping
    public String find(@RequestParam Long schoolId, Model model) {
        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(schoolId);

        School school = schoolRepository.findById(schoolId).orElse(null);
        model.addAttribute("school", school);

        model.addAttribute("educObjs", schoolEducObjs);
        model.addAttribute("title", "Преподавани предмети");
        model.addAttribute("schools", schoolRepository.findAll());

        return "educObjects/index";
    }


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

    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {
        educObjRepository.deleteById(id);

        attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));

        return new RedirectView("/educObjects");
    }


}
