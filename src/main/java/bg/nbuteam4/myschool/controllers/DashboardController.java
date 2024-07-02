package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.GlobalFiltersRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
            GlobalFiltersRequest globalFiltersRequest,
            Authentication authentication
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
            @RequestHeader(value = "Referer", required = false) String referer,
            RedirectAttributes attributes,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);

            return "redirect:" + referer;
        }

        if (globalFiltersRequest.getSchoolId() == null) {
            session.setAttribute("schoolId", null);

            return "redirect:" + referer;
        }

        Optional<School> school = schoolRepository.findById(globalFiltersRequest.getSchoolId());
        if (school.isEmpty()) {
            attributes.addFlashAttribute("result", new ActionResult("Избрали сте несъщуствуващо училище.", ActionResultType.ERROR));

            return "redirect:" + referer;
        }
        session.setAttribute("schoolId", school.get().getId());

        if (globalFiltersRequest.getStudyPeriodId() == null) {
            session.setAttribute("studyPeriodId", null);

            return "redirect:" + referer;
        }

        Optional<StudyPeriod> studyPeriod = studyPeriodRepository.findById(globalFiltersRequest.getStudyPeriodId());

        if (studyPeriod.isEmpty() || !studyPeriod.get().getSchool().getId().equals(school.get().getId())) {
            attributes.addFlashAttribute("result", new ActionResult("Избрали сте невалиден срок.", ActionResultType.ERROR));

            return "redirect:" + referer;
        }

        session.setAttribute("studyPeriodId", studyPeriod.get().getId());

        return "redirect:" + referer;
    }
}
