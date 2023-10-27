package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.Role;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.web.dto.ResetPasswordDto;
import capstone.hackathon.capstone.web.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
	User save(UserRegistrationDto registrationDto);
	User findByUsername(String username);
	User findByUserEmail(String userEmail);
	User removeUserRole(User user,String role);
	//String removeUserByUsername(String username);
	String removeUserByUserEmail(String userEmail);
	public String resetPassword(ResetPasswordDto resetPasswordDto);
	public List<User> getAllPanelists();
	User AddUserRole(User user, String role);

	User findByUserId(Long id);

	public Optional<User> updateUserRoleByEmail(String email, String role);


	String verifyEmail(String email, String otp);

	String regenerateOtp(String email);
}
