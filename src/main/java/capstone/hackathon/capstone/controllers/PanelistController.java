package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.service.*;
import capstone.hackathon.capstone.web.dto.MailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")

@RestController
@RequestMapping("/panelists")
public class PanelistController {

    @Autowired
    private PanelistService ideaService;

    @Autowired
    private IdeaService is;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AssignmentService assignmentService;
    // get by Panelist Id
    @GetMapping("/nullIdeas")
    public List<Idea> getNullStatusIdeas() {
        return ideaService.getNullStatusIdeas();
    }


    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateIdeaStatus(@RequestParam int ideaId, @RequestParam String status) {
        AssignedIdeas assignedIdea=assignmentService.findByIdeaId(ideaId);
        if(assignedIdea!=null) assignmentService.updateAssignedIdeaStatus(assignedIdea,status);
        ideaService.updateIdeaStatus(ideaId, status);
        Idea idea=is.findByIdeaId(ideaId);
        if(idea!=null)
        {
            Team team=idea.getTeam();
            User leader= userService.findByUserId(team.getLeaderId());
            MailMessages mailMessages= new MailMessages();
            if(status=="Rejected")
                emailService.sendMail(leader.getUserEmail(), "iHackathon Round 1 result",mailMessages.ideaRejection);
            if(status=="Approved")
                emailService.sendMail(leader.getUserEmail(), "iHackathon Round 1 result",mailMessages.ideaSelection);
        }
        return ResponseEntity.ok("Idea status updated successfully");
    }

    @GetMapping("/viewIdeas")
    public List<Idea> viewIdeas()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        List<Idea> ideas= assignmentService.getByPanelistId(user.getId());
        return ideas;
    }
}
