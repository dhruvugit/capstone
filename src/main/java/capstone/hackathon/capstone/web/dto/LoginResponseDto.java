package capstone.hackathon.capstone.web.dto;

public class LoginResponseDto {
    private String message;
    private String token;

    // Constructors, getters, and setters

    // Constructor with both message and token
    public LoginResponseDto(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
