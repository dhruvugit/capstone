package capstone.hackathon.capstone.controllers;


import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;
import capstone.hackathon.capstone.service.IdeaService;
import capstone.hackathon.capstone.entities.Idea;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api")
public class IdeaController {

    @Autowired
    private IdeaService is;

    @PreAuthorize("hasAuthority('Role_User')")
    @PostMapping("/ideas")
    public ResponseEntity<Idea> submitIdea(@RequestBody Idea idea) {
        return new ResponseEntity<>(is.submitIdea(idea), HttpStatus.CREATED);
    }

    @GetMapping("/ideas")
    public ResponseEntity<List<Idea>> getIdeas() {
        List<Idea> ideas = is.getIdeas();
        return new ResponseEntity<>(ideas, HttpStatus.OK);
    }

    @GetMapping("/ideas/{id}")
    public ResponseEntity<?> getIdea(@PathVariable Integer id) {
        try {
            Idea idea = is.getIdeaById(id);
            return new ResponseEntity<>(idea, HttpStatus.OK);
        } catch (IdeaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


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

    @PutMapping("/ideas")
    public ResponseEntity<Idea> updateIdea(@RequestBody Idea idea) {
        Idea updatedIdea = is.updateIdea(idea);
        return new ResponseEntity<>(updatedIdea, HttpStatus.OK);
    }

    @PutMapping("/ideas/team/{teamId}")
    public ResponseEntity<String> updateImplementationFields(
            @PathVariable Long teamId,
            @RequestParam(name = "newtitle") String newtitle,
            @RequestParam(name = "newsummary") String newsummary,
            @RequestParam(name = "newpdfUrl") String newpdfUrl) {
        try {
            is.updateIdeaFields(teamId, newtitle, newsummary, newpdfUrl);
            return ResponseEntity.ok("Fields updated successfully.");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred:"+e.getMessage());
        }
    }

    @PutMapping("/ideas/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Integer id, @RequestBody Map<String, String> requestBody) {
        ResponseEntity<String> response;
        try {
            String status = requestBody.get("status");
            is.updateStatus(id, status);
            response = new ResponseEntity<>("Status updated successfully.", HttpStatus.OK);
        } catch (IdeaNotFoundException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;
    }


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
