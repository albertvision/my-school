package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.School;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SchoolRepository extends ListCrudRepository<School, Long> {

    Optional<School> findById(Long id);


}