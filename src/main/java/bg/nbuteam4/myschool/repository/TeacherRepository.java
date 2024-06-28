package bg.nbuteam4.myschool.repository;


import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.entity.Teacher;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends ListCrudRepository<Teacher, Long> {

    Optional<Teacher> findById(Long id);
    Optional<Teacher> findBySchoolIdAndName(Long schoolId, String name);
    List<Teacher> findBySchoolId(Long schoolId);
}
