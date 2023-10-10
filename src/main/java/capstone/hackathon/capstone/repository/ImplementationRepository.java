package capstone.hackathon.capstone.repository;

import java.util.Optional;

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
    
    @Modifying
    @Transactional
    @Query("UPDATE Implementation i SET i.gitHubURL = :newgitHubURL, i.recordingURL = :newrecordingURL, i.pptURL = :newpptURL,i.description = :newdescription WHERE i.team.teamId = :teamId")
    void updateImplementationFields(@Param("teamId") Long teamId, @Param("newgitHubURL") String newField1, @Param("newrecordingURL") String newField2,@Param("newpptURL") String newField3,@Param("newdescription") String newField4);

}
