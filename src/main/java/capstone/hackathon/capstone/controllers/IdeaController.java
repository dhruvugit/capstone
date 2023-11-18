package capstone.hackathon.capstone.controllers;


import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;
import capstone.hackathon.capstone.repository.IdeaRepository;
import capstone.hackathon.capstone.repository.TeamRepository;
import capstone.hackathon.capstone.repository.UserRepository;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.service.IdeaService;
import capstone.hackathon.capstone.entities.Idea;


import capstone.hackathon.capstone.web.dto.ChangeStatusDto;
import capstone.hackathon.capstone.web.dto.IdeaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api")
public class IdeaController {
    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private IdeaService is;

    @Autowired
    EmailService emailService;

    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')" )
    @GetMapping("/ideas/nullStatus")
    public ResponseEntity<List<Idea>> getIdeasWithNullStatus() {
        List<Idea> ideas = is.getIdeasWithNullStatus();
        return new ResponseEntity<>(ideas, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Role_User')")
    @PostMapping("/ideas")
    public ResponseEntity<Idea> submitIdea(@RequestBody IdeaDto ideaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(is.submitIdea(ideaDto,user), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')" )
@GetMapping("/ideas")
public ResponseEntity<List<Map<String, Object>>> getIdeasWithTeamNames() {
    List<Idea> ideas = is.getIdeas(); // Assuming this method returns a List<Idea>

    List<Map<String, Object>> ideaResponses = new ArrayList<>();
    for (Idea idea : ideas) {
        Team team = idea.getTeam();
        String teamName = team != null ? team.getTeamName() : null;

        Map<String, Object> response = new HashMap<>();
        response.put("idea", idea);
        response.put("teamName", teamName);

        ideaResponses.add(response);
    }

    return new ResponseEntity<>(ideaResponses, HttpStatus.OK);
}




//    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')" )
//    @GetMapping("/ideas/{id}")
//    public ResponseEntity<?> getIdea(@PathVariable Integer id) {
//        try {
//            Idea idea = is.getIdeaById(id);
//            return new ResponseEntity<>(idea, HttpStatus.OK);
//        } catch (IdeaNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/ideas/{id}")
    public ResponseEntity<?> getIdea(@PathVariable Integer id) {
        try {
            Idea idea = is.getIdeaById(id);
            Team team = idea.getTeam();

            Map<String, Object> response = new HashMap<>();
            response.put("idea", idea);
            response.put("team", team);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IdeaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')" )
    @GetMapping("/ideas/team/{teamId}")
    public ResponseEntity<?> findIdeaByTeamId(@PathVariable Long teamId) {
        try {
            Idea idea = is.findIdeaByTeamId(teamId);
            return ResponseEntity.ok(idea);
        }
        catch (IdeaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('Role_Leader')" )
    @PutMapping("/ideas")
    public ResponseEntity<Idea> updateIdea(@RequestBody Idea idea) {
        Idea updatedIdea = is.updateIdea(idea);
        return new ResponseEntity<>(updatedIdea, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('Role_Leader') or hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @PutMapping("/ideas/{ideaId}")
    public ResponseEntity<String> updateIdeaFields(
            @PathVariable Integer ideaId,
            @RequestBody IdeaDto updatedIdeaDto) {
        try {
            Idea existingIdea = is.getIdeaById(ideaId);

            // Set the properties from the DTO
            existingIdea.setTitle(updatedIdeaDto.getTitle());
            existingIdea.setSummary(updatedIdeaDto.getSummary());
            existingIdea.setPdfUrl(updatedIdeaDto.getPdfUrl());

            // Save the updated idea back to the database
            ideaRepository.save(existingIdea);

            return ResponseEntity.ok("Fields updated successfully.");
        } catch (IdeaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Idea not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

//    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
//    @PutMapping("/ideas/updateStatus/{id}")
//    public ResponseEntity<String> updateStatus(
//            @PathVariable Integer id,
//            @RequestBody ChangeStatusDto changeStatusDto) {
//
//        ResponseEntity<String> response;
//        try {
//            String status = changeStatusDto.getStatus();
//            is.updateStatus(id, status);
//            response = new ResponseEntity<>("Status updated successfully.", HttpStatus.OK);
//        } catch (IdeaNotFoundException e) {
//            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//        return response;
//    }



    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @PutMapping("/ideas/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Integer id,
            @RequestBody ChangeStatusDto changeStatusDto) {

        ResponseEntity<String> response;
        try {
            String status = changeStatusDto.getStatus();
            String feedback = changeStatusDto.getFeedback();

            is.updateStatus(id, status);

            // Get the idea and team details
            Idea idea = ideaRepository.findIdeaById(id);

            if (idea.getTeam() != null) {
                Long teamId = idea.getTeam().getTeamId();
                Optional<Team> optionalTeam = teamRepository.findByTeamId(teamId);

                if (optionalTeam.isPresent()) {
                    Team team = optionalTeam.get();
                    Long leaderId = team.getLeaderId();
                    User leader = userRepository.findById(leaderId).orElse(null);





                    ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
                    emailExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (leader != null) {
                                    // Send email to leader of the team
                                    String subject = "Feedback and Status Update";
                                    String message;

                                    if (status.equalsIgnoreCase("approved")) {
                                        message = "Congratulations on the approval of your idea!\n\n" +
                                                "Idea Title: " + idea.getTitle() + "\n" +
                                                "Feedback: " + feedback + "\n\n" +
                                                "You have successfully advanced to the next round. Please proceed by submitting " +
                                                "the implementation details (code, presentation, recording) uploaded on your google drive account.\n\n" +

                                                "Best regards,\n" +
                                                "Team iHackathon";
                                    } else if (status.equalsIgnoreCase("rejected")) {
                                        message = "We appreciate your effort and creativity!\n\n" +
                                                "Idea Title: " + idea.getTitle() + "\n" +
                                                "Feedback: " + feedback + "\n\n" +
                                                "Although your idea was not selected this time, we encourage you to continue " +
                                                "innovating and participating in future events. Your ideas have great potential!\n\n" +
                                                "Best regards,\n" +
                                                "Team iHackathon";
                                    } else {
                                        message = "Idea Title: " + idea.getTitle() + "\n" +
                                                "Feedback: " + feedback + "\n\n" +
                                                "Status: " + status;
                                    }

                                    emailService.sendMail(leader.getUserEmail(), subject, message);
                                }

                            }
                            catch (Exception e) {
                                System.out.println("failed" + e);
                            }
                        }
                    });
                    emailExecutor.shutdown();

                    response = new ResponseEntity<>("Status updated successfully. Email sent.", HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>("Team not found for the given idea.", HttpStatus.NOT_FOUND);
                }
            } else {
                response = new ResponseEntity<>("No team associated with the given idea.", HttpStatus.NOT_FOUND);
            }
        } catch (IdeaNotFoundException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;
    }









    @PreAuthorize("hasAuthority('Role_Leader')" )
    @DeleteMapping("/ideas/{id}")
    public ResponseEntity<String> deleteIdea(@PathVariable Integer id) {
        try {
            is.deleteIdea(id);
            return new ResponseEntity<>("Idea with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (IdeaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
