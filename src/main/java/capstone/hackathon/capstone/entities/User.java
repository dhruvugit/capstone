package capstone.hackathon.capstone.entities;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="USER_TABLE",uniqueConstraints = @UniqueConstraint(columnNames = {"username","userEmail"}))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String userEmail;
	@Column(name = "active")
	private boolean active;
	private String otp;
	private LocalDateTime otpGeneratedTime;

	public LocalDateTime getOtpGeneratedTime() {
		return otpGeneratedTime;
	}

	public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonIgnore
	  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	  
	  @JoinTable( name = "users_roles", joinColumns = @JoinColumn( name =
	  "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
	  name = "role_id", referencedColumnName = "id"))
	  private List<Role> roles;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", userEmail=" + userEmail +  ", roles=" + roles
				+ "]";
	}
	public User(String username, String password, String firstName, String lastName, String userEmail, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
	
		this.roles = roles;
	}
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
}
