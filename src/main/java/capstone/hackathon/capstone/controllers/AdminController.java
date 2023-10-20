package capstone.hackathon.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.UserRoleRequestDto;

import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;


    @PreAuthorize("hasAuthority('Role_Admin')" )
    @PutMapping("/updateRole")
    public ResponseEntity<String> updateUserRoleByEmail(@RequestBody UserRoleRequestDto userRoleRequestDto) {

        String email = userRoleRequestDto.getUserEmail();
        String roleName = userRoleRequestDto.getRole();

        if (email != null && roleName != null) {
            Optional<User> updatedUser = userService.updateUserRoleByEmail(email, roleName);

            if (updatedUser.isPresent()) {
                return ResponseEntity.ok("User role updated successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or invalid request");
    }


	/*
	 * @Autowired private RoleService roleService;
	 */
//	@PreAuthorize("hasAuthority('Role_Admin')")
//	@PostMapping("/addRoletoUser")
//    public ResponseEntity<User> addUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
//        String userEmail = userRoleRequest.getUserEmail();
//        String roleName = userRoleRequest.getRole();
//
//		User existingUser = userService
//				.findByUsername(userEmail);
//
//        if (existingUser != null) {
//            User updatedUser = userService.AddUserRole(existingUser, roleName);
//            return ResponseEntity.ok(updatedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//
//    @PreAuthorize("hasAuthority('Role_Admin')")
//    @PostMapping("/removeRolefromUser")
//    public ResponseEntity<User> removeUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
//        String userEmail = userRoleRequest.getUserEmail();
//        String roleName = userRoleRequest.getRole();
//
//        User existingUser = userService.findByUserEmail(userEmail);
//
//        existingUser.setRoles(roleName);
//
//        if (existingUser != null ) {
//            // Remove the role from the user's roles
//            User updatedUser = userService.removeUserRole(existingUser, roleName);
//            return ResponseEntity.ok(updatedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//	}




}
