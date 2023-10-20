package capstone.hackathon.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.UserRegistrationDto;
import capstone.hackathon.capstone.entities.User;

@CrossOrigin("*")
@RestController
@RequestMapping("/registration")
public class RegistrationController {
	 @Autowired
	    private UserService userService;

	 @Autowired
	 private PasswordEncoder passwordEncoder;

	//@PreAuthorize("hasAuthority('Role_Admin') or hasAuthority('Role_User')")
	    @PostMapping
	 public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto registrationDto) {
	       
	            // Call the UserService to register the user
	        	System.out.println(registrationDto.toString());
				registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

	            User registeredUser = userService.save(registrationDto);
	            
	            if (registeredUser != null) {
	                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed.");
	            }
	        
	        
/*	        catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
	        }*/
	    }
	
	
	
	
	
}