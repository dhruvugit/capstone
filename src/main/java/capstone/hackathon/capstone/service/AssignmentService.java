package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.repository.AssignedIdeasRepository;
import capstone.hackathon.capstone.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AssignmentService {
    @Autowired
    private UserService userService;
    @Autowired
    private IdeaService ideaService;
    @Autowired
    private AssignedIdeasRepository assignedIdeasRepository;
    @Autowired
    private IdeaRepository ideaRepository;
    public String assignIdeas()
    {
        List<User> panelists=userService.getAllPanelists();
        List<Idea> ideas=ideaService.getIdeas();
        assignmentAlgorithm(panelists,ideas);
        return "Ideas have been assigned to the panelists successfully!";
    }

    private void assignmentAlgorithm(List<User> panelists, List<Idea> ideas) {

        Collections.shuffle(ideas, new Random());

        int ideasPerPanelist = ideas.size() / panelists.size();
        int remainingIdeas = ideas.size() % panelists.size();

        int ideaIndex = 0;

        for (User panelist : panelists) {


            // Assign an equal share of ideas to each panelist
            for (int i = 0; i < ideasPerPanelist; i++) {

                AssignedIdeas assignedIdeas=new AssignedIdeas(panelist.getId(),ideas.get(ideaIndex).getId());
                assignedIdeasRepository.save(assignedIdeas);
                ideaIndex++;
            }

            // Distribute any remaining ideas equally among panelists
            if (remainingIdeas > 0) {
                //assign
                AssignedIdeas assignedIdeas=new AssignedIdeas(panelist.getId(),ideas.get(ideaIndex).getId());
                assignedIdeasRepository.save(assignedIdeas);
                ideaIndex++;
                remainingIdeas--;
            }

            // Set the assigned ideas for this panelist


            // Optionally, you can save this assignment in your database
        }


    }

    public List<Idea> getByPanelistId(Long id) {
        List<AssignedIdeas> obj=assignedIdeasRepository.findByPanelistId(id);
        List<Idea> ideas= new ArrayList<>();
        for(AssignedIdeas assignedIdeas:obj)
        {
            ideas.add(ideaRepository.findIdeaById(assignedIdeas.getIdeaId()));
        }
        return ideas;

    }
}
