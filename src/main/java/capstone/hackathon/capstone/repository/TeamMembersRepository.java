package capstone.hackathon.capstone.repository;

import java.util.List;
import java.util.Optional;

import capstone.hackathon.capstone.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.TeamMembers;
@Repository("TeamMembersRepository")
public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long>{
	Optional<TeamMembers> findByMemberId(Long memberId);

	//List<TeamMembers> findByLeaderIdOrMembersMemberId(Long leaderId, Long memberId);

	List<TeamMembers> findByTeamId(Long teamId);




	
}
