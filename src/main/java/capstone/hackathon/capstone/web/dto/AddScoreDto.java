package capstone.hackathon.capstone.web.dto;


import java.util.List;

public class AddScoreDto {

    private String implementationId;
    private List<Integer> scores;

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

