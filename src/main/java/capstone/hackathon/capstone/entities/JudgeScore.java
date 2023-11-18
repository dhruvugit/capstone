package capstone.hackathon.capstone.entities;

import jakarta.persistence.*;

@Entity
@Table(name="JudgeScores")
public class JudgeScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long judgeId;
    private int implementationId;
    private String teamName;
    private int TechnicalProficiencyScore;
    private int PresentationAndCommunicationScore;
    private int CreativityAndInnovationScore;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long judgeId) {
        this.judgeId = judgeId;
    }

    public int getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(int implementationId) {
        this.implementationId = implementationId;
    }

    public int getTechnicalProficiencyScore() {
        return TechnicalProficiencyScore;
    }

    public void setTechnicalProficiencyScore(int technicalProficiencyScore) {
        TechnicalProficiencyScore = technicalProficiencyScore;
    }

    public int getPresentationAndCommunicationScore() {
        return PresentationAndCommunicationScore;
    }

    public void setPresentationAndCommunicationScore(int presentationAndCommunicationScore) {
        PresentationAndCommunicationScore = presentationAndCommunicationScore;
    }

    public int getCreativityAndInnovationScore() {
        return CreativityAndInnovationScore;
    }

    public void setCreativityAndInnovationScore(int creativityAndInnovationScore) {
        CreativityAndInnovationScore = creativityAndInnovationScore;
    }

    public JudgeScore(Long judgeId, int implementationId, String teamName, int technicalProficiencyScore, int presentationAndCommunicationScore, int creativityAndInnovationScore) {

        this.judgeId = judgeId;
        this.implementationId = implementationId;
        this.teamName = teamName;
        TechnicalProficiencyScore = technicalProficiencyScore;
        PresentationAndCommunicationScore = presentationAndCommunicationScore;
        CreativityAndInnovationScore = creativityAndInnovationScore;
    }

    public JudgeScore(){

    }

}