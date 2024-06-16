package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.Program;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends ListCrudRepository<Program, Long> {

    Optional<Program> findByStudyPeriodIdAndSchoolClassIdAndDayOfWeekAndLessonOrder(
            Long studyPeriodId, Long schoolClassId, DayOfWeek dayOfWeek, int lessonOrder);



    List<Program> findByStudyPeriodIdAndSchoolClassIdAndDayOfWeekOrderByLessonOrderAsc(Long studyPeriodId, Long schoolClassId, DayOfWeek dayOfWeek);


    List<Program> findByStudyPeriodIdAndSchoolClassId(Long studyPeriodId, Long schoolClassId);

    List<Program> findByTeacherId(Long teacherId);

    List<Program> findByEducObjId(Long educObjId);

    List<Program> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<Program> findByLessonOrder(int lessonOrder);

    List<Program> findByStudyPeriodId(Long studyPeriodId);

    List<Program> findBySchoolClassId(Long schoolClassId);

}

