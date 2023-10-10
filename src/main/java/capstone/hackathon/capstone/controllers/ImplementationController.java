package capstone.hackathon.capstone.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test1.impfile.exceptions.ImplementationNotFoundException;

import capstone.hackathon.capstone.entities.Implementation;
import capstone.hackathon.capstone.service.IfImplementationService;



@RestController
@RequestMapping(path="/user")
public class ImplementationController {
		
	@Autowired IfImplementationService implementationService;
	
	@PostMapping("/implementations")
	public ResponseEntity<Implementation> createImplementation(@RequestBody Implementation implementation){
		return new ResponseEntity<>(implementationService.createImplementation(implementation),HttpStatus.CREATED);
	}
	
	@GetMapping("/implementations")
	 public ResponseEntity<List<Implementation>> fetchAllImplementations() {
        List<Implementation> implementations = implementationService.fetchAllImplementations();
        return new ResponseEntity<>(implementations, HttpStatus.OK);
    }
	
	@GetMapping("/implementations/{implementationId}")
	public ResponseEntity<Implementation> getImplementationById(@PathVariable int implementationId) {
        try {
            Implementation implementation = implementationService.fetchImplementationById(implementationId);
            return new ResponseEntity<>(implementation, HttpStatus.OK);
        } catch (ImplementationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	 @GetMapping("/implementations/team/{teamId}")
	    public ResponseEntity<?> findImplementationByTeamId(@PathVariable Long teamId) {
	        try {
	            Implementation implementation = implementationService.findImplementationByTeamId(teamId);
	            return ResponseEntity.ok(implementation);
	        } catch (ImplementationNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
		
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
	
	@PutMapping("/implementations/team/{teamId}")
    public ResponseEntity<String> updateImplementationFields(
            @PathVariable Long teamId,
            @RequestParam(name = "newgitHubURL") String newgitHubURL,
            @RequestParam(name = "newrecordingURL") String newrecordingURL,
            @RequestParam(name = "newpptURL") String newpptURL,
            @RequestParam(name = "newdescription") String newdescription) {

        implementationService.updateImplementationFields(teamId, newgitHubURL, newrecordingURL,newpptURL,newdescription);
        
        return ResponseEntity.ok("Fields updated successfully.");
    }
	
	@PutMapping("/update/{teamId}/score")
    public ResponseEntity<String> updateScoreList(
            @PathVariable Long teamId,
            @RequestParam(name = "scoreList") List<Integer> scoreList) {

        // Call the service method to update the score list
        implementationService.updateScoreList(teamId, scoreList);

        return ResponseEntity.ok("Score list updated successfully.");
    }

	
	
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
