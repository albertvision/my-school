package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.SchoolClassEducObj;
import bg.nbuteam4.myschool.entity.TeachEduc;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolClassEducObjRepository extends ListCrudRepository<SchoolClassEducObj, Long> {

    Optional<SchoolClassEducObj> findById(Long id);

    List<SchoolClassEducObj> findByStudyPeriodIdAndSchoolClassId(Long studyPeriodId, Long schoolClassId);
}