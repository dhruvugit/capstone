package capstone.hackathon.capstone.web.dto;

import capstone.hackathon.capstone.entities.Role;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


public class LoginResponse {
    private String token;
    private List<Role> role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public LoginResponse(String token, List<Role> role) {
        this.token = token;
        this.role = role;
    }
}
