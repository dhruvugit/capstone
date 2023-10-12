package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;


import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.repository.IdeaRepository;

import capstone.hackathon.capstone.web.dto.IdeaDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("is")
public class IdeaService {

    @Autowired
    private IdeaRepository ir;

    public Idea submitIdea(IdeaDto ideaDto) {
        Idea idea = new Idea();
        idea.setPdfUrl(ideaDto.getPdfUrl());
        idea.setSummary(ideaDto.getSummary());
        idea.setTitle(ideaDto.getTitle());
        return ir.save(idea);
    }


    public List<Idea> getIdeas() {
        return ir.findAll();
    }

    public Idea getIdeaById(Integer id) {
        Optional<Idea> optionalIdea = ir.findById(id);

        if (optionalIdea.isPresent()) {
            return optionalIdea.get();
        } else {
            throw new IdeaNotFoundException("Idea with ID " + id + " not found.");
        }
    }



    public Idea updateIdea(Idea idea) {
        return ir.save(idea);
    }


    public void updateStatus(Integer id, String status) throws IdeaNotFoundException {
        Optional<Idea> optionalIdea = ir.findById(id);

        if (optionalIdea.isPresent()) {
            Idea idea = optionalIdea.get();
            idea.setStatus(status);
            ir.save(idea);
        } else {
            throw new IdeaNotFoundException("Idea with ID " + id + " not found.");
        }
    }


    public void deleteIdea(Integer id) throws IdeaNotFoundException {
        Optional<Idea> ideaOptional = ir.findById(id);

        if (ideaOptional.isPresent()) {
            ir.deleteById(id);
        } else {
            throw new IdeaNotFoundException("Idea with ID " + id + " not found.");
        }

    }



    public Idea findIdeaByTeamId(Long teamId){
        Optional<Idea> optionalIdea=ir.findIdeabyTeamId(teamId);
        return optionalIdea.orElseThrow(()->new IdeaNotFoundException("No Record found for team"+ teamId));
    }


    @Transactional
    public void updateIdeaFields(Long teamId, String newField1,String newField2,String newField3){
        ir.updateIdeaFields(teamId,newField1,newField2,newField3);
    }
}
