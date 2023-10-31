package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.web.dto.MailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    	String response=teamService.registerTeamMembers(team.getTeamId(), registerTeamDto);
        if(response!="Successfull!") {
			teamService.deleteById(team.getTeamId());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
//		MailMessages mailMessages=new MailMessages();



		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					MailMessages mailMessages=new MailMessages();
					emailService.sendMail(registerTeamDto.getLeaderEmail(),"Team registered",mailMessages.getTeamRegistration());

					if(registerTeamDto.getMember1Email()!="")
						emailService.sendMail(registerTeamDto.getMember1Email(),"Team registered",mailMessages.getTeamRegistration());
					if(registerTeamDto.getMember2Email()!="")
						emailService.sendMail(registerTeamDto.getMember2Email(),"Team registered",mailMessages.getTeamRegistration());
					if(registerTeamDto.getMember3Email()!="")
						emailService.sendMail(registerTeamDto.getMember3Email(),"Team registered",mailMessages.getTeamRegistration());
				} catch (Exception e) {
					System.out.println("failed" + e);
				}
			}
		});
		emailExecutor.shutdown();

		return ResponseEntity.status(HttpStatus.CREATED).body("Team and members registered successfully");
    }
}
