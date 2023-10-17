package capstone.hackathon.capstone.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.repository.IdeaRepository;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.AddScoreDto;
import capstone.hackathon.capstone.web.dto.ImplementationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import capstone.hackathon.capstone.exceptions.*;

import capstone.hackathon.capstone.entities.Implementation;
import capstone.hackathon.capstone.service.IfImplementationService;



@RestController
@RequestMapping(path="/api")
public class ImplementationController {

    @Autowired
    private IdeaRepository ideaRepository;
		
	@Autowired IfImplementationService implementationService;

    @PreAuthorize("hasAuthority('Role_Leader')" )
	@PostMapping("/implementations")
	public ResponseEntity<Implementation> createImplementation(@RequestBody Implementation implementation){
		return new ResponseEntity<>(implementationService.createImplementation(implementation),HttpStatus.CREATED);
	}

    @PostMapping("/submitImplementation")
    public ResponseEntity<Implementation> submitImplementation(@RequestBody ImplementationDto implementationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(implementationService.submitImplementation(implementationDto,user), HttpStatus.CREATED);

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




    @PreAuthorize("hasAuthority('Role_Leader') or hasAuthority('Role_User') or hasAuthority('Role_Judge')" )
	@GetMapping("/implementations/{implementationId}")
	public ResponseEntity<Implementation> getImplementationById(@PathVariable int implementationId) {
        try {
            Implementation implementation = implementationService.fetchImplementationById(implementationId);
            return new ResponseEntity<>(implementation, HttpStatus.OK);
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
	public ResponseEntity<Implementation> updateImplementationById(@PathVariable int implementationId, @RequestBody Implementation updatedImplementation) {
        try {
            Implementation implementation = implementationService.updateImplementationById(implementationId,updatedImplementation);
            return new ResponseEntity<>(implementation, HttpStatus.OK);
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
      
	
}
