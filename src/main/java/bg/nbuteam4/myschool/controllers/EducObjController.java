package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("educObjs", educObjRepository.findAll());
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


}
