package capstone.hackathon.capstone.web.dto;

import java.util.OptionalDouble;

public class TeamScoreResponse {
    private String teamName;
    private Double score;

    public TeamScoreResponse(String teamName, Double score) {
        this.teamName = teamName;
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
