package bg.nbuteam4.myschool.resolver;

import bg.nbuteam4.myschool.exception.InvalidGlobalFilterException;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class GlobalFiltersResolver implements HandlerMethodArgumentResolver {
    private final SchoolRepository schoolRepository;
    private final StudyPeriodRepository studyPeriodRepository;

    public GlobalFiltersResolver(SchoolRepository schoolRepository, StudyPeriodRepository studyPeriodRepository) {
        this.schoolRepository = schoolRepository;
        this.studyPeriodRepository = studyPeriodRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(GlobalFilter.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        GlobalFilter globalFilter = parameter.getParameterAnnotation(GlobalFilter.class);

        if (globalFilter == null) {
            return null;
        }

        String filterName = globalFilter.value();
        Object filterValue = session.getAttribute(getFilterSessionName(filterName));
        boolean filterRequired = globalFilter.required();

        if (filterValue == null) {
            return getEmptyValueCallback(filterName, filterRequired);
        }

        try {
            Object result = getFilterValue(filterName, filterValue);

            if (result == null) {
                return getEmptyValueCallback(filterName, filterRequired);
            }

            return result;
        } catch (NullPointerException e) {
            return getEmptyValueCallback(filterName, filterRequired);
        }
    }

    private String getFilterSessionName(String filterName) {
        return switch (filterName) {
            case "school" -> "schoolId";
            case "studyPeriod" -> "studyPeriodId";
            default -> null;
        };
    }

    private Object getFilterValue(String filterName, Object filterValue) {
        return switch (filterName) {
            case "school" -> schoolRepository.findById((Long) filterValue).orElse(null);
            case "studyPeriod" -> studyPeriodRepository.findById((Long) filterValue).orElse(null);
            default -> null;
        };
    }

    private Object getEmptyValueCallback(String filterName, boolean isRequired) {
        if (isRequired) {
            throw new InvalidGlobalFilterException(filterName);
        }

        return null;
    }
}
