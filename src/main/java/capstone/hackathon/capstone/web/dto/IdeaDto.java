package capstone.hackathon.capstone.web.dto;

public class IdeaDto {
    private String title;
    private String summary;
    private String pdfUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public IdeaDto(String title, String summary, String pdfUrl) {
        this.title = title;
        this.summary = summary;
        this.pdfUrl = pdfUrl;
    }

    @Override
    public String toString() {
        return "IdeaDto{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                '}';
    }


}
