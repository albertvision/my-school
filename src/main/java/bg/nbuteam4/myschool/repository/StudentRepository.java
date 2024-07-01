package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.Student;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends ListCrudRepository<Student, Long> {
    Optional<Student> findByEgn(String egn);

    Optional<Student> findByParentEgn(String parentEgn);

    Optional<Student> findByIdAndSchool(Long id, School school);

    List<Student> findBySchoolId(Long schoolId);

    @Transactional
    void deleteByIdAndSchool(Long id, School school);
}
