package capstone.hackathon.capstone.web.dto;


import java.util.List;

public class AddScoreDto {

    private String implementationId;
//    private List<Integer> scores;

    private int technicalProficiencyScore;
    private int creativityAndInnovationScore;
    private int presentationAndCommunicationScore;

    private String strengthFeedback;
    private String improvementAreaFeedback;
    private String developmentRecommendationsFeedback;

    public int getTechnicalProficiencyScore() {
        return technicalProficiencyScore;
    }

    public void setTechnicalProficiencyScore(int technicalProficiencyScore) {
        this.technicalProficiencyScore = technicalProficiencyScore;
    }

    public int getCreativityAndInnovationScore() {
        return creativityAndInnovationScore;
    }

    public void setCreativityAndInnovationScore(int creativityAndInnovationScore) {
        this.creativityAndInnovationScore = creativityAndInnovationScore;
    }

    public int getPresentationAndCommunicationScore() {
        return presentationAndCommunicationScore;
    }

    public void setPresentationAndCommunicationScore(int presentationAndCommunicationScore) {
        this.presentationAndCommunicationScore = presentationAndCommunicationScore;
    }

    public String getStrengthFeedback() {
        return strengthFeedback;
    }

    public void setStrengthFeedback(String strengthFeedback) {
        this.strengthFeedback = strengthFeedback;
    }



    public String getImprovementAreaFeedback() {
        return improvementAreaFeedback;
    }

    public void setImprovementAreaFeedback(String improvementAreaFeedback) {
        this.improvementAreaFeedback = improvementAreaFeedback;
    }

    public String getDevelopmentRecommendationsFeedback() {
        return developmentRecommendationsFeedback;
    }

    public void setDevelopmentRecommendationsFeedback(String developmentRecommendationsFeedback) {
        this.developmentRecommendationsFeedback = developmentRecommendationsFeedback;
    }

    public String getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(String implementationId) {
        this.implementationId = implementationId;
    }

//    public List<Integer> getScores() {
//        return scores;
//    }
//
//    public void setScores(List<Integer> scores) {
//        this.scores = scores;
//    }
}

