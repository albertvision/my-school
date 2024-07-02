package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.ClassStudent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassStudentRepository extends ListCrudRepository<ClassStudent, Long> {
    Optional<ClassStudent> findById(Long id);

    List<ClassStudent> findByStudyPeriodIdAndSchoolClassId(Long studyPeriodId, Long schoolClassId);
    List<ClassStudent> findByStudyPeriodIdAndSchoolClassIdOrderByStudentNumberInClass(Long studyPeriodId, Long schoolClassId);

    Optional<ClassStudent> findByStudyPeriodIdAndSchoolClassIdAndStudentNumberInClass(Long studyPeriodId, Long schoolClassId, int studentNumberInClass);

    @Query("""
            select cs
            from ClassStudent cs
            join SchoolClass sc on sc = cs.schoolClass
            where sc.school.id=:schoolId
                and cs.studyPeriod.id=:studyPeriodId
                and cs.student.id=:studentId
            """)
    Optional<ClassStudent> findBySchoolIdAndStudyPeriodIdAndStudentId(
            @Param("schoolId") Long schoolId,
            @Param("studyPeriodId") Long studyPeriodId,
            @Param("studentId") Long studentId
    );

    @Query("""
            select max(cs.studentNumberInClass)
            from ClassStudent cs
            where cs.schoolClass.id=:schoolClassId
            """)
    Optional<Integer> findHighestStudentNumberInSchoolClassId(Long schoolClassId);
}
