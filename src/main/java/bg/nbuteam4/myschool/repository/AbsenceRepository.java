package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.dto.AbsenceAndStudentNames;
import bg.nbuteam4.myschool.entity.Absence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends ListCrudRepository<Absence, Long> {

    Optional<Absence> findById(Long id);

    List<Absence> findByStudyPeriodIdAndSchoolClassId(Long studyPeriodId, Long schoolClassId);

    @Query("""
            SELECT new bg.nbuteam4.myschool.dto.AbsenceAndStudentNames(a.id, a.studyPeriod.id, a.schoolClass.id, cs.studentNumberInClass,
            a.absenceDate, a.educObj, a.absenceType, a.teacher.id, a.notes, a.status, CONCAT(s.firstName, ' ', s.middleName, ' ', s.lastName))
            FROM Absence a
            JOIN ClassStudent cs ON cs.studentNumberInClass = a.studentNumberInClass
            AND cs.studyPeriod.id = a.studyPeriod.id
            AND cs.schoolClass.id = a.schoolClass.id
            JOIN Student s ON s.id = cs.student.id
            WHERE a.studyPeriod.id = :studyPeriodId
            AND a.schoolClass.id = :schoolClassId
            ORDER BY a.id ASC
            """)
    List<AbsenceAndStudentNames> findByStudyPeriodIdAndSchoolClassIdWithStudentNamesOrderByIdAsc(@Param("studyPeriodId") Long studyPeriodId, @Param("schoolClassId") Long schoolClassId);

}
