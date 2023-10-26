package capstone.hackathon.capstone.web.dto;


import java.util.List;

public class AddScoreDto {

    private String implementationId;
    private List<Integer> scores;


    private String strengthFeedback;
    private String weaknessFeedback;
    private String developmentFeedback;

    public String getStrengthFeedback() {
        return strengthFeedback;
    }

    public void setStrengthFeedback(String strengthFeedback) {
        this.strengthFeedback = strengthFeedback;
    }

    public String getWeaknessFeedback() {
        return weaknessFeedback;
    }

    public void setWeaknessFeedback(String weaknessFeedback) {
        this.weaknessFeedback = weaknessFeedback;
    }

    public String getDevelopmentFeedback() {
        return developmentFeedback;
    }

    public void setDevelopmentFeedback(String developmentFeedback) {
        this.developmentFeedback = developmentFeedback;
    }

    public String getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(String implementationId) {
        this.implementationId = implementationId;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
}

