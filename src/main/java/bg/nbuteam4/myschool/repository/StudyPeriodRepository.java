package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.StudyPeriod;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyPeriodRepository extends ListCrudRepository<StudyPeriod, Long> {
    Optional<StudyPeriod> findById(Long id);

    Optional<StudyPeriod> findByCurrentYearAndCurrentSemesterAndSchoolId(Year currentYear, int currentSemester, Long schoolId);

    List<StudyPeriod> findBySchoolIdAndStatusEquals(Long schoolId, int status);

}


