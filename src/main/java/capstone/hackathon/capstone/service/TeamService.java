package capstone.hackathon.capstone.service;

import org.springframework.stereotype.Service;

import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.TeamMembers;
import capstone.hackathon.capstone.web.dto.RegisterTeamDto;

@Service
public interface TeamService {
	 Team registerTeam(RegisterTeamDto registerTeamDto);
	 String registerTeamMembers(Long teamId, RegisterTeamDto registerTeamDto);
	 Team findTeamByLeaderId(Long leaderId);
	 TeamMembers findByMemberId(Long memberId);
}
