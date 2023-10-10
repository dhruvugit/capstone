package capstone.hackathon.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.service.TeamService;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.RegisterTeamDto;

@RestController
@RequestMapping("/TeamRegistration")
public class TeamRegistrationController {

    @Autowired
    private TeamService teamService;
    
    @Autowired
    private UserService userService;

	@PreAuthorize("hasAuthority('Role_User')")
    @PostMapping
    public ResponseEntity<String> registerTeamAndMembers(@RequestBody RegisterTeamDto registerTeamDto) {
    	System.out.println(registerTeamDto.toString());
    	String leaderEmail=registerTeamDto.getLeaderEmail();
    	if(userService.findByUserEmail(leaderEmail)==null)
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Leader Email Address.");
    	}
    	if(teamService.findTeamByLeaderId(userService.findByUserEmail(leaderEmail).getId())!=null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerTeamDto.getLeaderName()+"is alreadly registered as a leader in another team.");
    	if(teamService.findByMemberId(userService.findByUserEmail(leaderEmail).getId())!=null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerTeamDto.getLeaderName()+"is alreadly registered as a member in another team.");
    	Team team=teamService.registerTeam(registerTeamDto);
    	String response=teamService.registerTeamMembers(team.getId(), registerTeamDto);
        if(response!="Successfull!") return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        return ResponseEntity.status(HttpStatus.CREATED).body("Team and members registered successfully");
    }
}
