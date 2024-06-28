package bg.nbuteam4.myschool.repository;


import bg.nbuteam4.myschool.entity.TypeMark;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TypeMarkRepository extends ListCrudRepository<TypeMark, Long> {

    Optional<TypeMark> findById(Long id);

    List<TypeMark> findBySchoolIdOrderByIdAsc(Long schoolId);
}
