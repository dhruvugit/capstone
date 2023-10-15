package capstone.hackathon.capstone.web.dto;

public class ImplementationDto {

    private  String gitHubURL;
    private  String recordingURL;
    private  String pptURL;
    private  String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ImplementationDto(String gitHubURL, String recordingURL, String pptURL, String description) {
        this.gitHubURL = gitHubURL;
        this.recordingURL = recordingURL;
        this.pptURL = pptURL;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ImplementationDto{" +
                "gitHubURL='" + gitHubURL + '\'' +
                ", recordingURL='" + recordingURL + '\'' +
                ", pptURL='" + pptURL + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
