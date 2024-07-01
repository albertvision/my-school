package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.ClassStudent;
import bg.nbuteam4.myschool.entity.SchoolClass;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassStudentRepository extends ListCrudRepository<ClassStudent, Long> {
    Optional<ClassStudent> findById(Long id);

    List<ClassStudent> findByStudyPeriodIdAndSchoolClassId(Long studyPeriodId, Long schoolClassId);
    List<ClassStudent> findByStudyPeriodIdAndSchoolClassIdOrderByStudentNumberInClass(Long studyPeriodId, Long schoolClassId);

    Optional<ClassStudent> findByStudyPeriodIdAndSchoolClassIdAndStudentNumberInClass(Long studyPeriodId, Long schoolClassId, int studentNumberInClass);
}
