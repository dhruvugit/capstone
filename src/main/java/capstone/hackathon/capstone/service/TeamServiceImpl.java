package capstone.hackathon.capstone.service;

import java.util.Optional;

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
	public Team registerTeam(RegisterTeamDto registerTeamDto) {
		// TODO Auto-generated method stub
		String teamName=registerTeamDto.getTeamName();
		String leaderEmail=registerTeamDto.getLeaderEmail();
		Optional<User> op= ur.findByUserEmail(leaderEmail);
		Team team= new Team();
		if(!op.isEmpty())
		{
			team.setLeaderId(op.get().getId());
			team.setTeamName(teamName);
			User user =us.AddUserRole(op.get(), "Role_Leader");
			return tr.save(team);
			
		}
		else return null;
	}
	@Override
	public String registerTeamMembers(Long teamId, RegisterTeamDto registerTeamDto) {
		// TODO Auto-generated method stub
		if(registerTeamDto.getMember1Email()!="") {
			String memberEmail=registerTeamDto.getMember1Email();
			Optional<User> op= ur.findByUserEmail(memberEmail);
			if(!op.isEmpty()) {
				Optional<TeamMembers> op1 = tmr.findByMemberId(op.get().getId());
				if(!op1.isEmpty()) {
					return registerTeamDto.getMember1Name()+" is already registered in another team";
				}
				TeamMembers tm=new TeamMembers(teamId, op.get().getId());
				User user =us.AddUserRole(op.get(), "Role_Member");
				tmr.save(tm);
			}
			else return "Team Member 1 Email is not registered.";
		}
		if(registerTeamDto.getMember2Email()!="") {
			String memberEmail=registerTeamDto.getMember2Email();
			Optional<User> op= ur.findByUserEmail(memberEmail);
			if(!op.isEmpty()) {
				Optional<TeamMembers> op1 = tmr.findByMemberId(op.get().getId());
				if(!op1.isEmpty()) {
					return registerTeamDto.getMember2Name()+" is already registered in another team";
				}

				TeamMembers tm=new TeamMembers(teamId, op.get().getId());
				User user =us.AddUserRole(op.get(), "Role_Member");
				tmr.save(tm);
			}
			else return "Team Member 2 Email is not registered.";
		}
		if(registerTeamDto.getMember3Email()!="") {
			String memberEmail=registerTeamDto.getMember3Email();
			Optional<User> op= ur.findByUserEmail(memberEmail);
			if(!op.isEmpty()) {
				Optional<TeamMembers> op1 = tmr.findByMemberId(op.get().getId());
				if(!op1.isEmpty()) {
					return registerTeamDto.getMember3Name()+" is already registered in another team";
				}

				TeamMembers tm=new TeamMembers(teamId, op.get().getId());
				User user =us.AddUserRole(op.get(), "Role_Member");
				tmr.save(tm);
			}
			else return "Team Member 3 Email is not registered.";
		}
				
		return "Successfull!";
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
