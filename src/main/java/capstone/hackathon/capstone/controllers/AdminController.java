package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.Role;
import capstone.hackathon.capstone.service.AssignmentService;
import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.service.IdeaService;
import capstone.hackathon.capstone.web.dto.MailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.UserRoleRequestDto;

import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasAuthority('Role_Admin')" )
    @PutMapping("/updateRole")
    public ResponseEntity<String> updateUserRoleByEmail(@RequestBody UserRoleRequestDto userRoleRequestDto) {

        String email = userRoleRequestDto.getUserEmail();
        String roleName = userRoleRequestDto.getRole();

        if (email != null && roleName != null) {
            Optional<User> updatedUser = userService.updateUserRoleByEmail(email, roleName);

            if (updatedUser.isPresent()) {
                return ResponseEntity.ok("User role updated successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or invalid request");
    }

    @PreAuthorize("hasAuthority('Role_Admin')" )
    @GetMapping("/getPanelists")
    public List<User> getPanelists()
    {
        List<User> panelist=userService.getAllPanelists();
        return panelist;
    }

    private int check = 0;
    @PreAuthorize("hasAuthority('Role_Admin')" )
    @GetMapping("/assignIdeasToPanelists")
    public ResponseEntity<String> assignIdeas()
    {
        if (check!=0){return ResponseEntity.badRequest().body("Ideas have already been assigned once");}
        String response=assignmentService.assignIdeas();
        check++;
        return ResponseEntity.ok(response);
    }



//    @PreAuthorize("hasAuthority('Role_Admin')")
//    @PostMapping("/removeRolefromUser")
//    public ResponseEntity<User> removeUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
//        String userEmail = userRoleRequest.getUserEmail();
//        String roleName = userRoleRequest.getRole();
//
//        User existingUser = userService.findByUserEmail(userEmail);
//
//
//        if (existingUser != null ) {
//            // Remove the role from the user's roles
//            User updatedUser = userService.removeUserRole(existingUser, roleName);
//            return ResponseEntity.ok(updatedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//  }




    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/removeRolefromUser")
    public ResponseEntity<User> removeUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
        String userEmail = userRoleRequest.getUserEmail();
        String roleName = userRoleRequest.getRole();

        User updatedUser = userService.removeRoleFromUserByEmail(userEmail, roleName);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sendReminderToPanelists")
    public ResponseEntity<String>sendReminder(){
        List<User> panelists= userService.getAllPanelists();




        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(User panelist:panelists) {
                        emailService.sendMail(
                                panelist.getUserEmail(),
                                "Reminder: Pending Idea Evaluations",
                                "Dear " + panelist.getFirstName() + ",\n\n" +
                                        "We hope this message finds you well.\n\n" +
                                        "This is a friendly reminder to complete your pending idea evaluations for the hackathon. Your input and feedback are highly valuable.\n\n" +
                                        "Please ensure that you finish the evaluations before the deadline.\n\n" +
                                        "Thank you for your time and contribution!\n\n" +
                                        "Best regards,\n" +
                                        "Team iHackathon"
                        );
                    }
                } catch (Exception e) {
                    System.out.println("failed" + e);
                }
            }
        });
        emailExecutor.shutdown();






        return ResponseEntity.ok("Reminder Sent to the panelists successfully!");
    }




    @GetMapping("/sendReminderToJudges")
    public ResponseEntity<String>sendRemindertoJudge(){
        List<User> judges= userService.getAllJudges();
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(User judge:judges) {
                        emailService.sendMail(
                                judge.getUserEmail(),
                                "Reminder: Pending Idea Evaluations",
                                "Dear " + judge.getFirstName() + ",\n\n" +
                                        "We hope this message finds you well.\n\n" +
                                        "This is a friendly reminder to complete your pending idea implementation evaluations for the hackathon. Your input and feedback are highly valuable.\n\n" +
                                        "Please ensure that you finish the evaluations before the deadline.\n\n" +
                                        "Thank you for your time and contribution!\n\n" +
                                        "Best regards,\n" +
                                        "Team iHackathon"
                        );
                    }
                } catch (Exception e) {
                    System.out.println("failed" + e);
                }
            }
        });
        emailExecutor.shutdown();


        return ResponseEntity.ok("Reminder Sent to the panelists successfully!");
    }



    @GetMapping("/checkPanelistProgress")
    public List<AssignedIdeas> checkPanelistProgress(String panelistEmail)
    {
        List<AssignedIdeas> ideas=new ArrayList<>();
        User panelist= userService.findByUserEmail(panelistEmail);
        if(panelist!=null)
        {
            return assignmentService.findAssignedIdeasByPanelistId(panelist.getId());
        }
        else return null;

    }

    @GetMapping("/assignIdeasToOtherPanelists")
    public ResponseEntity<String> assignIdeasToOtherPanelists(@RequestParam String panelistEmail)
    {   userService.removeRoleFromUserByEmail(panelistEmail, "Role_Panelist");
        List<User> panelists=userService.getAllPanelists();
        List<Idea> ideas=new ArrayList<>();
        User panelist= userService.findByUserEmail(panelistEmail);
        List<AssignedIdeas> assignedIdeas=assignmentService.findAssignedIdeasByPanelistId(panelist.getId());
        for(AssignedIdeas idea:assignedIdeas)
        {
            if(idea.getStatus()!=null) continue;
            Idea i=ideaService.getIdeaById(idea.getIdeaId());

            ideas.add(i);
        }
        System.out.println(panelists);
        System.out.println(ideas);
        assignmentService.assignmentAlgorithm(panelists,ideas);
        return ResponseEntity.ok("Ideas have been assigned to the rest of the panelists.");
    }



}