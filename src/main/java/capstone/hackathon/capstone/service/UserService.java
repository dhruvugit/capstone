package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.Role;
import capstone.hackathon.capstone.entities.User;
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
	User AddUserRole(User user, String role);

	public Optional<User> updateUserRoleByEmail(String email, String role);


}
