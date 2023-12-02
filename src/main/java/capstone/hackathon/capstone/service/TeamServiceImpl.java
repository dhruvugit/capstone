package capstone.hackathon.capstone.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import capstone.hackathon.capstone.security.UserInfoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.TeamMembers;
import capstone.hackathon.capstone.repository.TeamMembersRepository;
import capstone.hackathon.capstone.repository.TeamRepository;
import capstone.hackathon.capstone.repository.UserRepository;
import capstone.hackathon.capstone.web.dto.RegisterTeamDto;
@Service
public class TeamServiceImpl implements TeamService{
	@Autowired
    private TeamRepository tr;

    @Autowired
    private UserRepository ur;
    
    @Autowired
    private TeamMembersRepository tmr;



    
    @Autowired
    private UserService us;

	@Override
	public String registerTeam(Team team) {
		String code= generateTeamCode();
		team.setTeamCode(code);
		tr.save(team);
		return code;
	}


	private static final String ALLOWED_CHARACTERS = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
	private String generateTeamCode() {

		SecureRandom random = new SecureRandom();
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
			char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
			code.append(randomChar);
		}

		return code.toString();
	}
 @Override
	public String joinTeam(UserInfoUserDetails user, String teamCode) {
		Optional<TeamMembers> op=tmr.findByMemberId(user.getId());
		Optional<Team> op2=tr.findByLeaderId(user.getId());
		System.out.println("Checkpoint for team member.\n");
		if(!op2.isEmpty()) return "You have already created another team.";
		if(op.isEmpty())
		{
			Team team=findTeamByTeamCode(teamCode);
			List<TeamMembers> members=tmr.findByTeamId(team.getTeamId());
			if(members.size()>=3) return "This team doesn't have a vacancy";
			TeamMembers teamMembers=new TeamMembers(team.getTeamId(), user.getId());
			tmr.save(teamMembers);
			return "success";
		}
		else return "you have already joined another team";
	}



	@Override
	public Team findTeamByTeamCode(String teamCode) {
		Optional<Team> team= tr.findByTeamCode(teamCode);
		if(!team.isEmpty()) return team.get();
		else return null;
	}






	@Override
	public Team findTeamByLeaderId(Long leaderId) {
		// TODO Auto-generated method stub
		Optional<Team> op=tr.findByLeaderId(leaderId);
		if(!op.isEmpty()) return op.get();
		return null;
	}
	@Override
	public TeamMembers findByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		Optional<TeamMembers> op=tmr.findByMemberId(memberId);
		if(!op.isEmpty()) return op.get();
		return null;
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		tr.deleteById(id);

	}




	
}
