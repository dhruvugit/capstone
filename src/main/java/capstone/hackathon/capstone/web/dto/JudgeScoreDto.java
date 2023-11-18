package capstone.hackathon.capstone.web.dto;

public class JudgeScoreDto {
    //Team Name, Idea Name,Score1, score2,score3

    private String teamName;

    private int TechnicalProficiencyScore;
    private int PresentationAndCommunicationScore;
    private int CreativityAndInnovationScore;

    public JudgeScoreDto(String teamName, int technicalProficiencyScore, int presentationAndCommunicationScore, int creativityAndInnovationScore) {
        this.teamName = teamName;
        TechnicalProficiencyScore = technicalProficiencyScore;
        PresentationAndCommunicationScore = presentationAndCommunicationScore;
        CreativityAndInnovationScore = creativityAndInnovationScore;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
}