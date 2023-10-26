package capstone.hackathon.capstone.service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import capstone.hackathon.capstone.web.dto.OtpUtil;
import capstone.hackathon.capstone.web.dto.ResetPasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import capstone.hackathon.capstone.entities.Role;
import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.repository.RoleRepository;
import capstone.hackathon.capstone.repository.UserRepository;
import capstone.hackathon.capstone.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailService emailService;
	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	
	/*
	 * public UserServiceImpl(UserRepository userRepository, RoleRepository
	 * roleRepository) { super(); this.userRepository = userRepository;
	 * this.roleRepository = roleRepository; }
	 */
	@Override
	public User save(UserRegistrationDto registrationDto) {
		// TODO Auto-generated method stub
		System.out.println(registrationDto.toString());
		String otp=otpUtil.generateOtp();
		emailService.sendMail(registrationDto.getEmail(),"Email verification OTP","The OTP for email verification is "+otp);
		Role role= new Role("Role_User");
		List<Role> defaultRole= new ArrayList<Role>();
		defaultRole.add(role);
		User user= new User(registrationDto.getUsername(), registrationDto.getPassword(), 
				registrationDto.getFirstName(), registrationDto.getLastName(), 
				registrationDto.getEmail(),defaultRole);
		user.setOtp(otp);
		//user.setActive(false);
		user.setOtpGeneratedTime(LocalDateTime.now());
		return userRepository.save(user);
	}

	
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		
		  Optional<User> op=userRepository.findByUsername(username); if(!op.isEmpty())
		  return op.get(); 
		  else return null;
			/*
			 * User user=userRepository.findByUsername(username); return user;
			 */
	}

	@Override
	public User findByUserEmail(String userEmail) {
		// TODO Auto-generated method stub

		  Optional<User> op=userRepository.findByUserEmail(userEmail);
		  if(!op.isEmpty()) return op.get();
		  else return null;

		/* return userRepository.findByUserEmail(userEmail); */
	}
	

	@Override
	public String removeUserByUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		
		  Optional<User> op=userRepository.findByUserEmail(userEmail); if(!op.isEmpty())
		  { userRepository.delete(op.get()); 
		  return "User with username "+userEmail+" is deleted successfully!";
		  } 
		  else return
		  "No user found";
		 
		
	}

	@Override
	public User removeUserRole(User user, String role) {
		// TODO Auto-generated method stub
	        // Find the role by its ID (or any other unique identifier)

	        // Check if both the user and role exist
		 	List<Role> existingRoles=user.getRoles();
		 		existingRoles.remove(role);
				user.setRoles(existingRoles);


			return userRepository.save(user);
	    }

	public Optional<User> updateUserRoleByEmail(String email, String roleName) {
		Optional<User> optionalUser = userRepository.findByUserEmail(email);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Role roleToAdd = Role.fromString(roleName);
			user.getRoles().add(roleToAdd);
			userRepository.save(user);
			return Optional.of(user);
		}
		return Optional.empty();
	}

	@Override
	public String verifyEmail(String email, String otp) {
		User user =findByUserEmail(email);
		if(user==null) return "User not found, Please enter registered email.";
		if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<(2*60)){
			user.setActive(true);
			userRepository.save(user);
			return "OTP verified successfully. You can now login";
		}
		return "Please regenerate OTP and try again";
	}

	@Override
	public String regenerateOtp(String email) {
		User user =findByUserEmail(email);
		if(user==null) return "User not found, Please enter registered email.";
		String otp=otpUtil.generateOtp();

		emailService.sendMail(email,"Email verification OTP","The OTP for email verification is "+otp);
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "OTP sent on your email. Please verify within 2 minutes. After that OTP will not be valid.";
	}


	@Override
	public User AddUserRole(User user, String role) {
		// TODO Auto-generated method stub
		/*
		 * User existingUser = userRepository.findById(user.getId()).orElse(null);
		 *
		 * // Find the role by its ID (or any other unique identifier) Role existingRole
		 * = roleRepository.findById(role.getId()).orElse(null);
		 *
		 * // Check if both the user and role exist if (existingUser != null &&
		 * existingRole != null) {
		 */
		// Add the role to the user's roles
		List<Role> existingRoles=user.getRoles();
		Role r=new Role(role);
		existingRoles.add(r);
		user.setRoles(existingRoles);
		return userRepository.save(user);



	}

	@Override
	public User findByUserId(Long id) {
		Optional<User>op=userRepository.findById(id);
		if(!op.isEmpty()) return op.get();
		return null;
	}


	public String resetPassword(ResetPasswordDto resetPasswordDto) {

		String username = resetPasswordDto.getUsername();
		String otp = resetPasswordDto.getOtp();
		User user = userService.findByUsername(username);

		if (user == null) {
			return "User not found.";
		}

		if (!otp.equals(user.getOtp())) {
			return "Invalid OTP.";
		}

		String newPassword = resetPasswordDto.getNewPassword();
		String newPassword2 = resetPasswordDto.getNewPassword2();

		if (!newPassword.equals(newPassword2)) {
			return "New passwords do not match.";
		}


		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return "Password reset successful.";
	}




}
	
