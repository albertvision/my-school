package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.Absence;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface AbsenceRepository extends ListCrudRepository<Absence, Long> {

    Optional<Absence> findById(Long id);

}
