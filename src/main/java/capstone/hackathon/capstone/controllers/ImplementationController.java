package capstone.hackathon.capstone.controllers;

import java.util.*;

import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.repository.IdeaRepository;
import capstone.hackathon.capstone.repository.ImplementationRepository;
import capstone.hackathon.capstone.repository.TeamRepository;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.AddScoreDto;
import capstone.hackathon.capstone.web.dto.ImplementationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.exceptions.*;

import capstone.hackathon.capstone.entities.Implementation;
import capstone.hackathon.capstone.service.IfImplementationService;

@CrossOrigin("*")

@RestController
@RequestMapping(path="/api")
public class ImplementationController {

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ImplementationRepository implementationRepository;

		
	@Autowired IfImplementationService implementationService;

    @PreAuthorize("hasAuthority('Role_Leader')" )
	@PostMapping("/implementations")
	public ResponseEntity<Implementation> createImplementation(@RequestBody Implementation implementation){
		return new ResponseEntity<>(implementationService.createImplementation(implementation),HttpStatus.CREATED);
	}

    @PostMapping("/submitImplementation")
    public ResponseEntity<?> submitImplementation(@RequestBody ImplementationDto implementationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();

        // Get the associated team
        Optional<Team> team = teamRepository.findByLeaderId(user.getId());

        if (team.isPresent()) {
            // Get the associated idea
            Long teamId = team.get().getTeamId();
            List<Idea> ideaList = ideaRepository.findByTeamId(teamId);

            Idea idea = !ideaList.isEmpty() ? ideaList.get(0) : null;

            if (idea != null && idea.getStatus().equals("approved")) {
                Implementation implementation = implementationService.submitImplementation(implementationDto, user);
                return new ResponseEntity<>(implementation, HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Idea must be approved before submitting an implementation.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be a leader of a team to submit an implementation.");
        }
    }





    @PreAuthorize("hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')")
    @GetMapping("/implementations")
    public ResponseEntity<List<Map<String, Object>>> fetchAllImplementationsWithTeamNames() {
        List<Implementation> implementations = implementationService.fetchAllImplementations();

        List<Map<String, Object>> implementationResponses = new ArrayList<>();
        for (Implementation implementation : implementations) {
            Team team = implementation.getTeam();
            String teamName = team != null ? team.getTeamName() : null;

            List<Idea> ideaList = ideaRepository.findByTeamId(implementation.getTeam().getTeamId());
            Idea idea = !ideaList.isEmpty() ? ideaList.get(0) : null;

            Map<String, Object> response = new HashMap<>();
            response.put("implementation", implementation);
            response.put("teamName", teamName);
            response.put("ideaTitle", idea != null ? idea.getTitle() : null);

            implementationResponses.add(response);
        }

        return new ResponseEntity<>(implementationResponses, HttpStatus.OK);
    }




    @PreAuthorize("hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')")
    @GetMapping("/implementations/{implementationId}")
    public ResponseEntity<Map<String, Object>> getImplementationById(@PathVariable int implementationId) {
        try {
            Implementation implementation = implementationService.fetchImplementationById(implementationId);

            Team team = implementation.getTeam();
            String teamName = team != null ? team.getTeamName() : null;

            List<Idea> ideaList = ideaRepository.findByTeamId(team.getTeamId());
            Idea idea = !ideaList.isEmpty() ? ideaList.get(0) : null;

            Map<String, Object> response = new HashMap<>();
            response.put("implementation", implementation);
            response.put("teamName", teamName);
            response.put("idea", idea);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ImplementationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')" )
	 @GetMapping("/implementations/team/{teamId}")
	    public ResponseEntity<?> findImplementationByTeamId(@PathVariable Long teamId) {
	        try {
	            Implementation implementation = implementationService.findImplementationByTeamId(teamId);
	            return ResponseEntity.ok(implementation);
	        } catch (ImplementationNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
    @PreAuthorize("hasAuthority('Role_Leader')" )
    @PutMapping("/implementations/{implementationId}")
    public ResponseEntity<Implementation> updateImplementationById(@PathVariable int implementationId, @RequestBody ImplementationDto updatedImplementationDto) {
        try {
            Implementation existingImplementation = implementationService.fetchImplementationById(implementationId);

            // Set the properties from the DTO
            existingImplementation.setGitHubURL(updatedImplementationDto.getGitHubURL());
            existingImplementation.setRecordingURL(updatedImplementationDto.getRecordingURL());
            existingImplementation.setPptURL(updatedImplementationDto.getPptURL());
            existingImplementation.setDescription(updatedImplementationDto.getDescription());

            // Save the updated implementation back to the database
            implementationRepository.save(existingImplementation);

            return new ResponseEntity<>(existingImplementation, HttpStatus.OK);
        } catch (ImplementationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PreAuthorize("hasAuthority('Role_Leader')" )
	@PutMapping("/implementations/team/{teamId}")
    public ResponseEntity<String> updateImplementationFields(
            @PathVariable Long teamId,
            @RequestParam(name = "newgitHubURL") String newgitHubURL,
            @RequestParam(name = "newrecordingURL") String newrecordingURL,
            @RequestParam(name = "newpptURL") String newpptURL,
            @RequestParam(name = "newdescription") String newdescription) {
        try {
            implementationService.updateImplementationFields(teamId, newgitHubURL, newrecordingURL, newpptURL, newdescription);

            return ResponseEntity.ok("Fields updated successfully.");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred:"+e.getMessage());
        }
    }

//    @PreAuthorize("hasAuthority('Role_Judge') or hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')" )
//	@PutMapping("/update/{teamId}/score")
//    public ResponseEntity<String> updateScoreList(
//            @PathVariable Long teamId,
//            @RequestParam(name = "scoreList") List<Integer> scoreList) {
//
//        // Call the service method to update the score list
//        implementationService.updateScoreList(teamId, scoreList);
//
//        return ResponseEntity.ok("Score list updated successfully.");
//    }


    @PreAuthorize("hasAuthority('Role_Judge') or hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')" )
    @PostMapping("/implementations/addScores")
    public ResponseEntity<String> addScores(@RequestBody AddScoreDto addScoreDto) {
        implementationService.addScores(addScoreDto);
        return new ResponseEntity<>("Scores added successfully.", HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('Role_Leader')" )
	@DeleteMapping("/implementations/{implementationId}")
	public ResponseEntity<String> deleteImplementationById(@PathVariable int implementationId) {
		try {
        	implementationService.deleteImplementationById(implementationId);
            return new ResponseEntity<>("Implementation with ID " + implementationId + " deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (ImplementationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }   
		 }



//    @GetMapping("/team-scores")
//    public ResponseEntity<?> getTeamScores() {
//        try {
//            return ResponseEntity.ok(implementationService.getTeamScores());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//        }
//    }




      
	
}
