package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.dto.AbsenceAndStudentNames;
import bg.nbuteam4.myschool.dto.MarkAndStudentNames;
import bg.nbuteam4.myschool.dto.SchoolAverageMark;
import bg.nbuteam4.myschool.entity.Absence;
import bg.nbuteam4.myschool.entity.Mark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends ListCrudRepository<Mark, Long> {

    Optional<Mark> findById(Long id);

    @Query("""
            SELECT new bg.nbuteam4.myschool.dto.MarkAndStudentNames(m.id, m.studyPeriod.id, m.schoolClass.id, cs.studentNumberInClass, m.educObj, m.teacher.id, m.value,  m.typeMark, m.status,
            m.dateTime, CONCAT(s.firstName, ' ', s.middleName, ' ', s.lastName))
            FROM Mark m
            JOIN ClassStudent cs ON cs.studentNumberInClass = m.studentNumberInClass
            AND cs.studyPeriod.id = m.studyPeriod.id
            AND cs.schoolClass.id = m.schoolClass.id
            JOIN Student s ON s.id = cs.student.id
            WHERE m.studyPeriod.id = :studyPeriodId
            AND m.schoolClass.id = :schoolClassId
            ORDER BY m.id ASC
            """)
    List<MarkAndStudentNames> findByStudyPeriodIdAndSchoolClassIdWithStudentNamesOrderByIdAsc(@Param("studyPeriodId") Long studyPeriodId, @Param("schoolClassId") Long schoolClassId);

    @Query("""
            SELECT new bg.nbuteam4.myschool.dto.SchoolAverageMark(s, avg(m.value))
            FROM School s
            left join SchoolClass sc on sc.school=s
            left join Mark m on sc=m.schoolClass
            group by s
            """)
    List<SchoolAverageMark> findAverageSchoolMarks();

}
