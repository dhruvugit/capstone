package capstone.hackathon.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.TeamMembers;
@Repository("TeamMembersRepository")
public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long>{
	Optional<TeamMembers> findByMemberId(Long memberId);
	
}
