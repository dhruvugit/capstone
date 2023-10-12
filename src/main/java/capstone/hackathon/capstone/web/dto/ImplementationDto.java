package capstone.hackathon.capstone.web.dto;

import capstone.hackathon.capstone.entities.Team;
import jakarta.persistence.*;

import java.util.List;

public class ImplemenatationDto {
    private int implementationId;

    private String gitHubURL;

    private String recordingURL;

    private String pptURL;

    private String description;

    @ElementCollection
    private List<Integer> score;

    private Team team;

    public int getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(int implementationId) {
        this.implementationId = implementationId;
    }

    public String getGitHubURL() {
        return gitHubURL;
    }

    public void setGitHubURL(String gitHubURL) {
        this.gitHubURL = gitHubURL;
    }

    public String getRecordingURL() {
        return recordingURL;
    }

    public void setRecordingURL(String recordingURL) {
        this.recordingURL = recordingURL;
    }

    public String getPptURL() {
        return pptURL;
    }

    public void setPptURL(String pptURL) {
        this.pptURL = pptURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "ImplemenatationDto{" +
                "implementationId=" + implementationId +
                ", gitHubURL='" + gitHubURL + '\'' +
                ", recordingURL='" + recordingURL + '\'' +
                ", pptURL='" + pptURL + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", team=" + team +
                '}';
    }
}
