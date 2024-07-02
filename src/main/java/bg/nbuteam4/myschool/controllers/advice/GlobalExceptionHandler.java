package bg.nbuteam4.myschool.controllers.advice;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.exception.InvalidGlobalFilterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidGlobalFilterException.class)
    public String handleInvalidGlobalFilters(
            InvalidGlobalFilterException ex,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute(
                "result",
                new ActionResult("Невалиден филтър: " + ex.getFilterName(), ActionResultType.ERROR)
        );

        return "redirect:/";
    }
}
