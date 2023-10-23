package capstone.hackathon.capstone.repository;

import java.util.List;
import java.util.Optional;

import capstone.hackathon.capstone.entities.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.Implementation;
import jakarta.transaction.Transactional;

@Repository("implementationRepository")
public interface ImplementationRepository extends JpaRepository<Implementation, Integer>{
    Optional<Implementation> findByTeam_TeamId(Long teamId);

    @Query("SELECT i FROM Implementation i JOIN i.team t WHERE t.teamId = :teamId")
    List<Implementation> findByTeamId(Long teamId);


    
    @Modifying
    @Transactional
    @Query("UPDATE Implementation i SET i.gitHubURL = :newgitHubURL, i.recordingURL = :newrecordingURL, i.pptURL = :newpptURL,i.description = :newdescription WHERE i.team.teamId = :teamId")
    void updateImplementationFields(@Param("teamId") Long teamId, @Param("newgitHubURL") String newField1, @Param("newrecordingURL") String newField2,@Param("newpptURL") String newField3,@Param("newdescription") String newField4);


//    @Query("SELECT i, SUM(s.score) " +
//            "FROM Implementation i " +
//            "JOIN i.score s " +
//            "GROUP BY i " +
//            "ORDER BY i.implementationId")
//    List<Object[]> getTeamScores();

}
