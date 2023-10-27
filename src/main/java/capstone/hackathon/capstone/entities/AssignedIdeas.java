package capstone.hackathon.capstone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AssignedIdeas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long panelistId;
    private int ideaId;


    public AssignedIdeas(){
        super();
    }
    public AssignedIdeas(Long panelistId, int ideaId) {
        this.panelistId = panelistId;
        this.ideaId = ideaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getPanelistId() {
        return panelistId;
    }

    public void setPanelistId(Long panelistId) {
        this.panelistId = panelistId;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }
}
