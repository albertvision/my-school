package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.dto.SchoolAverageMark;
import bg.nbuteam4.myschool.entity.Mark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MarkRepository extends ListCrudRepository<Mark, Long> {
    @Query("""
            SELECT new bg.nbuteam4.myschool.dto.SchoolAverageMark(s, avg(m.value))
            FROM School s
            left join SchoolClass sc on sc.school=s
            left join Mark m on sc=m.schoolClass
            group by s
            """)
    List<SchoolAverageMark> findAverageSchoolMarks();

}
