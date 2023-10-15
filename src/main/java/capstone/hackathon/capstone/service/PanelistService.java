package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PanelistService {

    @Autowired
    private IdeaRepository ideaRepository;

    public List<Idea> getNullStatusIdeas() {
        return ideaRepository.findByStatusIsNull();
    }

    public void updateIdeaStatus(int ideaId, String status) {
        Idea idea = ideaRepository.findById(ideaId).orElse(null);
        if (idea != null) {
            idea.setStatus(status);
            ideaRepository.save(idea);
        }
    }
}
