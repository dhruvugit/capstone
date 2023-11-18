package capstone.hackathon.capstone.web.dto;

public class IdeasByPanelistIdDto {
    private String ideaName;
    private String teamName;
    private String status;

    public IdeasByPanelistIdDto(String ideaName, String teamName, String status) {
        this.ideaName = ideaName;
        this.teamName = teamName;
        this.status = status;
    }

    public String getIdeaName() {
        return ideaName;
    }

    public void setIdeaName(String ideaName) {
        this.ideaName = ideaName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
