package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.entity.StudyPeriod;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends ListCrudRepository<SchoolClass, Long> {
    Optional<SchoolClass> findById(Long id);
    Optional<SchoolClass> findBySchoolIdAndName(Long schoolId, String name);
    List<SchoolClass> findBySchoolId(Long schoolId);
}
