package capstone.hackathon.capstone.web.dto;

public class UserRegistrationDto {
	private String username;
	private String firstName;
	private String lastName;
	private String Email;
	private String password;
	
	public UserRegistrationDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRegistrationDto(String username, String firstName, String lastName, String email, String password) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		Email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserRegistrationDto [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", Email=" + Email + ", password=" + password + "]";
	}
	
}
