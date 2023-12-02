package capstone.hackathon.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.Team;
@Repository("TeamRepository")
public interface TeamRepository extends JpaRepository<Team, Long>{
	Optional<Team> findByLeaderId(Long leaderId);

	Optional<Team> findByTeamId(Long teamId);
	Optional<Team> findByTeamCode(String teamCode);
}
