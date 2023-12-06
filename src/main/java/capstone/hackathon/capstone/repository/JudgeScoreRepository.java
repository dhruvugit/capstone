package capstone.hackathon.capstone.repository;

import capstone.hackathon.capstone.entities.JudgeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeScoreRepository extends JpaRepository<JudgeScore,Integer> {
    List<JudgeScore> findByJudgeId(Long id);
//    List<Integer> findImplementationIdByJudgeId(Long id);

    @Query("SELECT js.implementationId FROM JudgeScore js WHERE js.judgeId = :id")
    List<Integer> findImplementationIdByJudgeId(@Param("id") Long id);
}