package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.StudyPeriod;
import bg.nbuteam4.myschool.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;
@Repository
public interface StudyPeriodRepository extends ListCrudRepository<StudyPeriod, Long> {
    Optional<StudyPeriod> findById(Long id);
    Optional<StudyPeriod> findByCurrentYearAndCurrentSemesterAndSchoolId(Year currentYear, int currentSemester, Long schoolId);
    Optional<StudyPeriod> findBySchoolIdAndStatusEquals(Long schoolId, int status);

}


