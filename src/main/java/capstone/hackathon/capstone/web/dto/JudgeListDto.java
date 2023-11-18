package capstone.hackathon.capstone.web.dto;

public class JudgeListDto {
    //id,name,email,username
    private Long id;
    private String name;
    private String username;
    private String userEmail;

    public JudgeListDto(Long id, String name, String username, String userEmail) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}