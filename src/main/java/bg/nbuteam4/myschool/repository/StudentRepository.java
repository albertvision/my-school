package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface StudentRepository extends ListCrudRepository<Student, Long> {
    Optional<Student> findById(Long id);
}
