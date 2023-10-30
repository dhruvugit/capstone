package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.repository.AssignedIdeasRepository;
import capstone.hackathon.capstone.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssignmentService {

    @Autowired
    private EmailService emailService;
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

    public void assignmentAlgorithm(List<User> panelists, List<Idea> ideas) {

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
            emailService.sendMail(panelist.getUserEmail(),
                    "Ideas Assigned for Evaluation",
                    "Dear " + panelist.getFirstName() + ",\n\n" +
                            "We hope this message finds you well.\n\n" +
                            "We are pleased to inform you that a set of ideas have been assigned to you for evaluation. We kindly request you to evaluate these ideas in a timely manner. Please consider the following points while evaluating:\n" +
                            "Your valuable feedback is essential for us to make informed decisions. Please provide clear and constructive feedback for each idea, including any strengths, weaknesses, and potential areas for improvement.\n\n" +
                            "We appreciate your prompt attention to this matter.\n\n" +
                            "If you encounter any challenges or have any questions during the evaluation process, please do not hesitate to reach out to us.\n\n" +
                            "Regards,\n" +
                            "Team iHackathon"
                            );



            // Optionally, you can save this assignment in your database
        }


    }

    public void updateAssignedIdeaStatus(AssignedIdeas assignedIdeas, String status){
        assignedIdeas.setStatus(status);
        assignedIdeasRepository.save(assignedIdeas);
    }

//    public AssignedIdeas findByIdeaId(int id){
//        // AssignedIdeas idea=AssignedIdeasRepository.;
//         List<AssignedIdeas> idea = AssignedIdeasRepository.findByIdeaId(id);
//          return idea.get(0);;
//
//          //not audible baad mein hee karte hain
//    }


//    public AssignedIdeas findByIdeaId(int id)
//    {return AssignedIdeasRepository.findByIdeaId().get(0);}

    public AssignedIdeas findByIdeaId(int id)
    {
        Optional<AssignedIdeas> obj= assignedIdeasRepository.findByIdeaId(id);
        if(!obj.isEmpty())
        {
            return obj.get();
        }
        return null;
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




    public List<AssignedIdeas> findAssignedIdeasByPanelistId(Long id)
    {
        return assignedIdeasRepository.findByPanelistId(id);
    }




       // Inject AssignmentService for access to assigned ideas


}

