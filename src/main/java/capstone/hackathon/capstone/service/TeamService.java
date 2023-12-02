package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.security.UserInfoUserDetails;
import org.springframework.stereotype.Service;

import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.TeamMembers;
import capstone.hackathon.capstone.web.dto.RegisterTeamDto;

@Service
public interface TeamService {

	 Team findTeamByLeaderId(Long leaderId);
	 TeamMembers findByMemberId(Long memberId);
	public String joinTeam(UserInfoUserDetails user, String teamCode);



	public String registerTeam(Team team);
	void deleteById(Long id);
	public Team findTeamByTeamCode(String teamCode);
}
