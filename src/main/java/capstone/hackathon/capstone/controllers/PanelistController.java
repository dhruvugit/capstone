package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.service.PanelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/panelists")
public class PanelistController {

    @Autowired
    private PanelistService ideaService;

    @GetMapping("/nullIdeas")
    public List<Idea> getNullStatusIdeas() {
        return ideaService.getNullStatusIdeas();
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateIdeaStatus(@RequestParam int ideaId, @RequestParam String status) {
        ideaService.updateIdeaStatus(ideaId, status);
        return ResponseEntity.ok("Idea status updated successfully");
    }
}
