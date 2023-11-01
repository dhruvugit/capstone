package capstone.hackathon.capstone.web.dto;

import java.util.List;

public class UserRoleResponse {
    private List<String> roles;

    public UserRoleResponse(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
