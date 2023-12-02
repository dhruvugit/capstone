package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.entities.TeamMembers;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.web.dto.MailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.service.TeamService;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.RegisterTeamDto;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin("*")

@RestController
@RequestMapping("/TeamRegistration")
public class TeamRegistrationController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

	@Autowired
	private EmailService emailService;


	@PreAuthorize("hasAuthority('Role_User')")
	@PostMapping("/createTeam")
	public ResponseEntity<String> createTeam(@RequestBody RegisterTeamDto registerTeamDto)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
		Team isTeamLeader=teamService.findTeamByLeaderId(user.getId());
		if(isTeamLeader!=null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already created another team.");
		TeamMembers isTeamMember=teamService.findByMemberId(user.getId());
		if(isTeamMember!=null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already joined another team.");
		Team team=new Team();
		team.setTeamName(registerTeamDto.getTeamName());
		team.setTeamSummary(registerTeamDto.getTeamSummary());
		team.setLeaderId(user.getId());
		String response= teamService.registerTeam(team);
		userService.AddUserRole(userService.findByUsername(user.getUsername()),"Role_Leader");
		return ResponseEntity.ok(response);

	}


	@PreAuthorize("hasAuthority('Role_User')")
	@PostMapping("/joinTeam")
	public ResponseEntity<String> joinTeam(@RequestParam String teamCode)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
		System.out.println("Calling teamService\n");
		Team team=teamService.findTeamByTeamCode(teamCode);
		if(team==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid team code.");
		String response=teamService.joinTeam(user,teamCode);
		System.out.println("Response received from teamService: "+response);
		if(response=="success")
		{
			ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
			emailExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String subject = "Team Created for iHackathon";
						String messageBody = "Dear " + user.getUsername() + ",\n\n"
								+ "Congratulations! Your team for iHackathon has been successfully created.\n"
								+ "Team Code: " + response + "\n\n"
								+ "Please share this Team Code with your teammates so they can join your team.\n\n"
								+ "Good luck to you and your team in the competition!\n\n"
								+ "Regards,\n"
								+ "Team iHackathon";

						emailService.sendMail(user.getEmail(), subject, messageBody);

					} catch (Exception e) {
						System.out.println("failed" + e);
					}
				}
			});
			emailExecutor.shutdown();
			return ResponseEntity.ok("Team Joined Successfully!");
		}
		else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}






