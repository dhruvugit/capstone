package capstone.hackathon.capstone.web.dto;


import java.util.List;

public class AddScoreDto {

    private int implementationId;
    private List<Integer> scores;

    public int getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(int implementationId) {
        this.implementationId = implementationId;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
}

