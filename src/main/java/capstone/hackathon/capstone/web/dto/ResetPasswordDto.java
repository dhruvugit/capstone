package capstone.hackathon.capstone.web.dto;

public class ResetPasswordDto {
	private String username;
	private String otp;
	private String newPassword;
	private String newPassword2;

	// Getters and setters (or Lombok annotations if you're using Lombok)


	public ResetPasswordDto(String username, String otp, String newPassword, String newPassword2) {
		this.username = username;
		this.otp = otp;
		this.newPassword = newPassword;
		this.newPassword2 = newPassword2;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
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
		return "ResetPasswordDto [username=" + username + ", otp=" + otp + ", newPassword="
				+ newPassword + ", newPassword2=" + newPassword2 + "]";
	}
}
