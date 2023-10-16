package capstone.hackathon.capstone.controllers;


import capstone.hackathon.capstone.entities.*;
import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;
import capstone.hackathon.capstone.repository.*;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ImplementationRepository implementationRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private TeamMembersRepository teamMembersRepository;


        @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/userDetails")
    public ResponseEntity<UserInfoUserDetails> userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/dashboard/idea")
    public ResponseEntity<?> getUserIdea() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        // Step 1: Try to find the team where the user is the leader
        Optional<Team> optionalTeam = teamRepository.findByLeaderId(userId);

        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            List<Idea> ideas = ideaRepository.findByTeamId(team.getTeamId());
            return ResponseEntity.ok(ideas);
        } else {
            // Step 2: If the user is not a leader, try to find the team where they are a member
            Optional<TeamMembers> optionalMemberTeam = teamMembersRepository.findByMemberId(userId);

            if (optionalMemberTeam.isPresent()) {
                TeamMembers teamMembers = optionalMemberTeam.get();
                Long teamId = teamMembers.getTeamId();
                List<Idea> ideas = ideaRepository.findByTeamId(teamId);
                return ResponseEntity.ok(ideas);
            } else {
                // Step 3: If no team is found in either case, return "No Team Found"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Team Found");
            }
        }
    }



    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/dashboard/impl")
    public ResponseEntity<?> getUserImpl() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        // Step 1: Try to find the team where the user is the leader
        Optional<Team> optionalTeam = teamRepository.findByLeaderId(userId);

        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            List<Implementation> implementations = implementationRepository.findByTeamId(team.getTeamId());
            return ResponseEntity.ok(implementations);
        } else {
            // Step 2: If the user is not a leader, try to find the team where they are a member
            Optional<TeamMembers> optionalMemberTeam = teamMembersRepository.findByMemberId(userId);

            if (optionalMemberTeam.isPresent()) {
                TeamMembers teamMembers = optionalMemberTeam.get();
                Long teamId = teamMembers.getTeamId();
                List<Implementation> implementations = implementationRepository.findByTeamId(teamId);
                return ResponseEntity.ok(implementations);
            } else {
                // Step 3: If no team is found in either case, return "No Team Found"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Team Found");
            }
        }
    }








}



