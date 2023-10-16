package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.exceptions.IdeaNotFoundException;


import capstone.hackathon.capstone.entities.Idea;
import capstone.hackathon.capstone.repository.IdeaRepository;

import capstone.hackathon.capstone.repository.TeamRepository;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.IdeaDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("is")
public class IdeaService {

    @Autowired
    private IdeaRepository ir;

    @Autowired
    private TeamRepository teamRepository;

    public Idea submitIdea(IdeaDto ideaDto, UserInfoUserDetails user) {
        Team team = teamRepository.findByLeaderId(user.getId()).get();
        Idea idea = new Idea();
        idea.setPdfUrl(ideaDto.getPdfUrl());
        idea.setSummary(ideaDto.getSummary());
        idea.setTitle(ideaDto.getTitle());
        idea.setTeam(team);
        return ir.save(idea);
    }


    public List<Idea> getIdeas() {
        return new ArrayList<>(ir.findAll());
    }



    public List<Idea> getIdeasWithNullStatus() {
        return ir.findIdeasWithNullStatus();
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
        Idea idea = ir.findById(id).orElseThrow(() -> new IdeaNotFoundException("Idea with ID " + id + " not found"));
        idea.setStatus(status);
        ir.save(idea);
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
