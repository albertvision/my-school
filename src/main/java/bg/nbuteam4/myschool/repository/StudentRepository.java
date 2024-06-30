package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.Student;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends ListCrudRepository<Student, String> {
    Optional<Student> findByEgn(String egn);

    Optional<Student> findByParentEGN(String parentEGN);
    List<Student> findBySchoolId(Long schoolId);

    @Transactional
    void deleteByEgn(String egn);
}
