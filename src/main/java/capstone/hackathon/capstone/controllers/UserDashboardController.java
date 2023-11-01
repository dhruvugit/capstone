package capstone.hackathon.capstone.controllers;


import capstone.hackathon.capstone.entities.*;
import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;
import capstone.hackathon.capstone.repository.*;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.UserRoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")

@RestController
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private UserRepository userRepository;

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

            // Fetch the team name
            String teamName = team.getTeamName();

            // Create a response map
            Map<String, Object> response = new HashMap<>();
            response.put("teamName", teamName);
            response.put("ideas", ideas);

            return ResponseEntity.ok(response);
        } else {
            // Step 2: If the user is not a leader, try to find the team where they are a member
            Optional<TeamMembers> optionalMemberTeam = teamMembersRepository.findByMemberId(userId);

            if (optionalMemberTeam.isPresent()) {
                TeamMembers teamMembers = optionalMemberTeam.get();
                Long teamId = teamMembers.getTeamId();
                List<Idea> ideas = ideaRepository.findByTeamId(teamId);

                // Fetch the team name
                Optional<Team> optionalTeamForMember = teamRepository.findByTeamId(teamId);
                String teamName = optionalTeamForMember.map(Team::getTeamName).orElse("Team Name Not Available");

                // Create a response map
                Map<String, Object> response = new HashMap<>();
                response.put("teamName", teamName);
                response.put("ideas", ideas);

                return ResponseEntity.ok(response);
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






    /*@PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/dashboard/teamDetails")
    public ResponseEntity<?> getTeamDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        // Try to find the team where the user is the leader
        Optional<Team> optionalTeam = teamRepository.findByLeaderId(userId);

        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();

            // Fetch the leader details from User entity
            Optional<User> optionalLeader = userRepository.findById(team.getLeaderId());

            if (optionalLeader.isPresent()) {
                User leader = optionalLeader.get();

                // Fetch the team members from team_members_table
                List<TeamMembers> teamMembersList = teamMembersRepository.findByTeamId(team.getTeamId());
                List<User> members = new ArrayList<>();

                for (TeamMembers teamMembers : teamMembersList) {
                    Optional<User> optionalUser = userRepository.findById(teamMembers.getMemberId());
                    optionalUser.ifPresent(members::add);
                }

                // Create a map to hold the team details
                Map<String, Object> teamDetails = new HashMap<>();
                teamDetails.put("leader", leader);
                teamDetails.put("members", members);

                return ResponseEntity.ok(teamDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leader not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Team Found");
        }*/

    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/dashboard/teamDetails")
    public ResponseEntity<?> getTeamDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        Optional<Team> optionalTeam;

        if (userHasRole(user, "Role_User")) {
            // If the user has the "Role_User" role, find the team they are a part of using the teamMembersRepository.
            Optional<TeamMembers> teamMembers = teamMembersRepository.findByMemberId(userId);

            if (teamMembers.isPresent()) {

                optionalTeam = teamRepository.findById(teamMembers.get().getTeamId());
                if (optionalTeam.isPresent()) {
                    Team team = optionalTeam.get();
                    User leader = userRepository.findById(team.getLeaderId()).orElse(null);

                    if (leader != null) {
                        List<TeamMembers> teamMembersList = teamMembersRepository.findByTeamId(team.getTeamId());
                        List<User> members = new ArrayList<>();

                        for (TeamMembers teamMember : teamMembersList) {
                            Optional<User> optionalUser = userRepository.findById(teamMember.getMemberId());
                            optionalUser.ifPresent(members::add);
                        }

                        Map<String, Object> teamDetails = new HashMap<>();
                        teamDetails.put("leader", leader);
                        teamDetails.put("members", members);

                        return ResponseEntity.ok(teamDetails);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leader not found");
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Team Found");
                }
            }
        } if (userHasRole(user, "Role_Leader")) {
            // If the user has "Role_Panelist" or "Role_Leader" role, try to find the team where the user is the leader.
            optionalTeam = teamRepository.findByLeaderId(userId);


            if (optionalTeam.isPresent()) {
                Team team = optionalTeam.get();
                User leader = userRepository.findById(team.getLeaderId()).orElse(null);

                if (leader != null) {
                    List<TeamMembers> teamMembersList = teamMembersRepository.findByTeamId(team.getTeamId());
                    List<User> members = new ArrayList<>();

                    for (TeamMembers teamMembers : teamMembersList) {
                        Optional<User> optionalUser = userRepository.findById(teamMembers.getMemberId());
                        optionalUser.ifPresent(members::add);
                    }

                    Map<String, Object> teamDetails = new HashMap<>();
                    teamDetails.put("leader", leader);
                    teamDetails.put("members", members);

                    return ResponseEntity.ok(teamDetails);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leader not found");
                }
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Team Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Team is found ");
    }

    private boolean userHasRole(UserInfoUserDetails user, String roleName) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleName));
    }


    @PreAuthorize("hasAuthority('Role_Panelist') or hasAuthority('Role_Leader') or hasAuthority('Role_User')")
    @GetMapping("/userRole")
    public ResponseEntity<UserRoleResponse> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();

        // Get the user's roles
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserRoleResponse userRoleResponse = new UserRoleResponse(roles);

        return ResponseEntity.ok(userRoleResponse);
    }




}

