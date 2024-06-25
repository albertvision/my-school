package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.dto.GlobalFiltersRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class DashboardController {

    private final SchoolRepository schoolRepository;
    private final StudyPeriodRepository studyPeriodRepository;

    public DashboardController(SchoolRepository schoolRepository, StudyPeriodRepository studyPeriodRepository) {
        this.schoolRepository = schoolRepository;
        this.studyPeriodRepository = studyPeriodRepository;
    }


    @GetMapping
    String index(
            Model model,
            HttpSession session,
            GlobalFiltersRequest globalFiltersRequest
    ) {

        Long selectedSchoolId = (Long) session.getAttribute("schoolId");

        if (selectedSchoolId != null) {
            model.addAttribute(
                    "studyPeriods",
                    studyPeriodRepository.findBySchoolIdAndStatusEquals(selectedSchoolId, 1)
            );
        }


        model.addAttribute("title", "Табло");
        model.addAttribute("schools", schoolRepository.findAll());


        return "dashboard/index";
    }

    @PostMapping("set-global-filters")
    String setGlobalFilters(
            @Valid @ModelAttribute GlobalFiltersRequest globalFiltersRequest,
            RedirectAttributes attributes,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);

            return "redirect:/";
        }

        if (globalFiltersRequest.getSchoolId() == null) {
            session.setAttribute("schoolId", null);

            return "redirect:/";
        }

        Optional<School> school = schoolRepository.findById(globalFiltersRequest.getSchoolId());
        if (school.isEmpty()) {
            attributes.addFlashAttribute("result", new ActionResult("Избрали сте несъщуствуващо училище.", ActionResultType.ERROR));

            return "redirect:/";
        }
        session.setAttribute("schoolId", school.get().getId());

        if (globalFiltersRequest.getStudyPeriodId() == null) {
            session.setAttribute("studyPeriodId", null);

            return "redirect:/";
        }

        Optional<StudyPeriod> studyPeriods = studyPeriodRepository.findById(globalFiltersRequest.getStudyPeriodId());

        if (studyPeriods.isEmpty() || !studyPeriods.get().getSchool().getId().equals(school.get().getId())) {
            attributes.addFlashAttribute("result", new ActionResult("Избрали сте невалиден срок.", ActionResultType.ERROR));

            return "redirect:/";
        }

        session.setAttribute("studyPeriodId", studyPeriods.get().getId());

        return "redirect:/";
    }

    @GetMapping("/schools")
    String schools() {
        System.out.println("Tyk sme!");
        return "schools";
    }
}
