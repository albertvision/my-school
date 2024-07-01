package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.TeachEduc;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeachEducRepository extends ListCrudRepository<TeachEduc, Long> {

    Optional<TeachEduc> findById(Long id);

    Optional<TeachEduc> findBySchoolIdAndTeacherIdAndEducObjId(Long SchoolId, Long teacherId, Long educObjId);

    List<TeachEduc> findBySchoolIdAndTeacherId(Long SchoolId, Long teacherId);

    @Transactional
    void deleteBySchoolIdAndTeacherId(Long SchoolId, Long teacherId);
}