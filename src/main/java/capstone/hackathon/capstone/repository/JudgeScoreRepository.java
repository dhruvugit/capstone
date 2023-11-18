package capstone.hackathon.capstone.repository;

import capstone.hackathon.capstone.entities.JudgeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeScoreRepository extends JpaRepository<JudgeScore,Integer> {
    List<JudgeScore> findByJudgeId(Long id);
}