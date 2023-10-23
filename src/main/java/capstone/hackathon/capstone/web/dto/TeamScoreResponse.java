package capstone.hackathon.capstone.web.dto;

public class TeamScoreResponse {
    private String teamName;
    private int score;

    public TeamScoreResponse(String teamName, int score) {
        this.teamName = teamName;
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
