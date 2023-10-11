package capstone.hackathon.capstone.web.dto;

public class ResetPasswordDto {
	private String username;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPassword2() {
		return newPassword2;
	}
	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
	@Override
	public String toString() {
		return "ResetPasswordDto [username=" + username + ", oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + ", newPassword2=" + newPassword2 + "]";
	}
	public ResetPasswordDto(String username, String oldPassword, String newPassword, String newPassword2) {
		super();
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.newPassword2 = newPassword2;
	}
	public ResetPasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
