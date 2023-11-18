package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.Role;
import capstone.hackathon.capstone.repository.UserRepository;
import capstone.hackathon.capstone.service.*;
import capstone.hackathon.capstone.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.entities.User;

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
    private UserRepository userRepository;

    @Autowired
    private ImplementationService implementationService;

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasAuthority('Role_Admin')")
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

    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/getPanelists")
    public List<User> getPanelists() {
        List<User> panelist = userService.getAllPanelists();
        return panelist;
    }



    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/assignIdeasToPanelists")
    public ResponseEntity<String> assignIdeas() {

        String response = assignmentService.assignIdeas();

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
    public ResponseEntity<String> sendReminder() {
        List<User> panelists = userService.getAllPanelists();


        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (User panelist : panelists) {
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
    public ResponseEntity<String> sendRemindertoJudge() {
        List<User> judges = userService.getAllJudges();
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (User judge : judges) {
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
    public List<AssignedIdeas> checkPanelistProgress(String panelistEmail) {
        List<AssignedIdeas> ideas = new ArrayList<>();
        User panelist = userService.findByUserEmail(panelistEmail);
        if (panelist != null) {
            return assignmentService.findAssignedIdeasByPanelistId(panelist.getId());
        } else return null;

    }

    @GetMapping("/assignIdeasToOtherPanelists")
    public ResponseEntity<String> assignIdeasToOtherPanelists(@RequestParam String panelistEmail)
    {   //userService.removeRoleFromUserByEmail(panelistEmail, "Role_Panelist");
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
        assignmentService.assignmentAlgorithm(panelists,ideas);
        assignmentService.removeIdeasAssignedToPanelist(panelist.getId());
        return ResponseEntity.ok("Ideas have been assigned to the rest of the panelists.");
    }
    @GetMapping("/getPanelistsDetails")
    public ResponseEntity<List<PanelistListDto>> getAllPanelists() {
        List<User> panelists = userService.getAllPanelists();
        List<PanelistListDto> l = new ArrayList<>(); // Initialize the list

        for (User u : panelists) {
            PanelistListDto p = new PanelistListDto(u.getId(), u.getFirstName(), u.getUserEmail());
            l.add(p);
        }
        return ResponseEntity.ok(l);
    }


    @GetMapping("/getIdeasByPanelistId")
    public ResponseEntity<List<IdeasByPanelistIdDto>> getIdeasByPanelistId(@RequestParam Long id) {
        List<Idea> ideas = assignmentService.getByPanelistId(id);
        List<IdeasByPanelistIdDto> response = new ArrayList<>();;
        for (Idea i : ideas) {
            IdeasByPanelistIdDto ideasByPanelistIdDto = new IdeasByPanelistIdDto(i.getTitle(), i.getTeam().getTeamName(), i.getStatus());
            response.add(ideasByPanelistIdDto);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/sendReminderToIndividualJudge")
    public ResponseEntity<String> sendReminderToIndividualJudge(@RequestParam("email") String email) {
        Optional<User> judgeOptional = userRepository.findByUserEmail(email); // Assuming userRepository is your Spring Data JPA repository

        if (judgeOptional.isPresent()) {

            ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
            emailExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        User judge = judgeOptional.get();
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
                    } catch (Exception e) {
                        System.out.println("failed" + e);
                    }
                }
            });
            emailExecutor.shutdown();


            return ResponseEntity.ok("Reminder Sent to the Judge successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Judge not found for the provided email");
        }
    }


//    @GetMapping("/sendReminderToIndividualPanelist")
//    public ResponseEntity<String> sendReminderToIndividualPanelist() {
//        List<User> panelists = userService.getAllPanelists();
//        for (User panelist : panelists) {
//            emailService.sendMail(
//                    panelist.getUserEmail(),
//                    "Reminder: Pending Idea Evaluations",
//                    "Dear " + panelist.getFirstName() + ",\n\n" +
//                            "We hope this message finds you well.\n\n" +
//                            "This is a friendly reminder to complete your pending idea implementation evaluations for the hackathon. Your input and feedback are highly valuable.\n\n" +
//                            "Please ensure that you finish the evaluations before the deadline.\n\n" +
//                            "Thank you for your time and contribution!\n\n" +
//                            "Best regards,\n" +
//                            "Team iHackathon"
//            );
//
//        }
//        return ResponseEntity.ok("Reminder Sent to the Panelist successfully!");
//
//
//    }


    @GetMapping("/sendReminderToIndividualPanelist")
    public ResponseEntity<String> sendReminderToIndividualPanelist(@RequestParam("email") String email) {
        Optional<User> userOptional = userRepository.findByUserEmail(email); // Assuming userRepository is your Spring Data JPA repository

        if (userOptional.isPresent()) {

            ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
            emailExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        User panelist = userOptional.get();
                        emailService.sendMail(
                                panelist.getUserEmail(),
                                "Reminder: Pending Idea Evaluations",
                                "Dear " + panelist.getFirstName() + ",\n\n" +
                                        "We hope this message finds you well.\n\n" +
                                        "This is a friendly reminder to complete your pending idea implementation evaluations for the hackathon. Your input and feedback are highly valuable.\n\n" +
                                        "Please ensure that you finish the evaluations before the deadline.\n\n" +
                                        "Thank you for your time and contribution!\n\n" +
                                        "Best regards,\n" +
                                        "Team iHackathon"
                        );
                    } catch (Exception e) {
                        System.out.println("failed" + e);
                    }
                }
            });
            emailExecutor.shutdown();


            return ResponseEntity.ok("Reminder Sent to the Panelist successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Panelist not found for the provided email");
        }
    }




    @GetMapping("/getListOfAllJudges")
    public ResponseEntity<List<JudgeListDto>> getAllJudges()
    {
        List<User> judges=userService.getAllJudges();
        List<JudgeListDto> response=new ArrayList<>();
        for(User j:judges)
        {
            JudgeListDto ju=new JudgeListDto(j.getId(),j.getFirstName(),j.getUsername(),j.getUserEmail());
            response.add(ju);
        }
        return ResponseEntity.ok(response);
    }


    @PutMapping("/AssignIndividualIdea")
    public ResponseEntity<String> assignIndividualIdea(@RequestParam int ideaId, @RequestParam String panelistEmail )
    {
        String response= assignmentService.assignIndividualIdea(ideaId,panelistEmail);
        if(response=="Panelist not found") return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        else return ResponseEntity.ok(response);
    }



    @DeleteMapping("/removeAllAssignedIdeas")
    public ResponseEntity<String> removeAllAssignedIdeas()
    {
        return ResponseEntity.ok(assignmentService.removeAllAssignedIdeas());
    }



    @GetMapping("/GetJudgesScore")
    public ResponseEntity<List<JudgeScoreDto>> findJudgeScores(Long id)
    {
        List<JudgeScoreDto>judgeScoreDtos= implementationService.findScoresByJudgeId(id);
        return ResponseEntity.ok(judgeScoreDtos);

    }









}