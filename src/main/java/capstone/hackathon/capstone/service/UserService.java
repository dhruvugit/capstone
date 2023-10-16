package capstone.hackathon.capstone.service;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.web.dto.UserRegistrationDto;

public interface UserService {
	User save(UserRegistrationDto registrationDto);
	User findByUsername(String username);
	User findByUserEmail(String userEmail);
	User AddUserRole(User user,String role);
	User removeUserRole(User user,String role);
	//String removeUserByUsername(String username);
	String removeUserByUserEmail(String userEmail);

}
