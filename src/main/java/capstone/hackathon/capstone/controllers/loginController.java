package capstone.hackathon.capstone.controllers;
import capstone.hackathon.capstone.web.dto.LoginResponse;
import org.springframework.security.crypto.password.PasswordEncoder;


import capstone.hackathon.capstone.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.LoginRequestDto;
import capstone.hackathon.capstone.web.dto.ResetPasswordDto;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class loginController {
	@Autowired
    private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;


	//@PreAuthorize("hasAuthority('Role_User') or hasAuthority('Role_Admin') or hasAuthority('Role_Judge') or hasAuthority('Role_Panelist')")

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequest) {
		User user = userService.findByUsername(loginRequest.getUsername());
		if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			// Authentication successful, generate and return a token
			String token = jwtService.generateToken(loginRequest.getUsername());
			LoginResponse response = new LoginResponse(token, user.getRoles());
			return ResponseEntity.ok(response); // Return the response entity with a 200 OK status
		} else {
			// Authentication failed
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Return 401 Unauthorized
		}
	}


	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto)
	{
		User user= userService.findByUserEmail(resetPasswordDto.getUsername());
		if(user==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User username doesnot exist");
		if(resetPasswordDto.getOldPassword()!=user.getPassword())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
		if(resetPasswordDto.getNewPassword()!=resetPasswordDto.getNewPassword2())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password doesn't match the re-entered new password");
		return null;
	}





//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
//		User user = userService.findByUsername(loginRequest.getUsername());
//
//		if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//			// Authentication successful
//			return ResponseEntity.ok("Login successful!");
//		} else {
//			// Authentication failed
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//		}
//	}


	//@PreAuthorize("hasAuthority('Role_User') or hasAuthority('Role_Admin') or hasAuthority('Role_Judge') or hasAuthority('Role_Panelist')")



//	@PostMapping("/authenticate")
//	public String authenticateAndGetToken(@RequestBody LoginRequestDto loginRequestDto) {
//
//
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
//		if (authentication.isAuthenticated()) {
//			return jwtService.generateToken(loginRequestDto.getUsername());
//		} else {
//			throw new UsernameNotFoundException("invalid user request !");
//		}
//
//	}


	
	
}