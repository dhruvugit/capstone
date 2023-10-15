package capstone.hackathon.capstone.repository;
import capstone.hackathon.capstone.entities.Idea;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ir")
public interface IdeaRepository extends JpaRepository<Idea, Integer> {
    @Query("SELECT i FROM Idea i JOIN i.team t WHERE t.teamId = :teamId")
    Optional<Idea> findIdeabyTeamId(Long teamId);

    //@Query("SELECT i FROM Idea i WHERE i.status IS NULL")
//    List<Idea> findIdeasWithNullStatus();

//   List<Idea> findByStatus(String status);

//    @Query("SELECT i FROM Idea i WHERE i.status IS NULL")
//    List<Idea> findIdeasWithNullStatus();

//    @Query(value = "SELECT * FROM idea_table WHERE status IS NULL", nativeQuery = true)
//    List<Idea> findIdeasWithNullStatus();

    // List<Idea> findByStatusIsNull();

   // List<Idea> findByStatusIsNull();


    @Modifying
    @Transactional
    @Query("UPDATE Idea i SET i.title = :newtitle, i.summary = :newsummary, i.pdfUrl = :newpdfUrl WHERE i.team.teamId = :teamId")
    void updateIdeaFields(@Param("teamId") Long teamId, @Param("newtitle") String newField1, @Param("newsummary") String newField2, @Param("newpdfUrl") String newField3);

    List<Idea> findByStatusIsNull();
}
