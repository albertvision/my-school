package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.EducObj;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface EducObjRepository extends ListCrudRepository<EducObj, Long> {

 Optional<EducObj> findById(Long id);


 List<EducObj> findBySchoolId(Long schoolId);

}
