package capstone.hackathon.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.service.UserService;
import capstone.hackathon.capstone.web.dto.UserRoleRequestDto;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	/*
	 * @Autowired private RoleService roleService;
	 */
	@PreAuthorize("hasAuthority('Role_Admin')")
	@PostMapping("/addRoletoUser")
    public ResponseEntity<User> addUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
        String username = userRoleRequest.getUsername();
        String roleName = userRoleRequest.getRole();

		User existingUser = userService
				.findByUsername(username);/*
											 * Role existingRole = roleService.findRoleByName(roleName);
											 * if(existingRole==null) System.out.println("No role found");
											 */
		
        if (existingUser != null) {
            // Add the role to the user's roles
            User updatedUser = userService.AddUserRole(existingUser, roleName);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/removeRolefromUser")
    public ResponseEntity<User> removeUserRole(@RequestBody UserRoleRequestDto userRoleRequest) {
        String username = userRoleRequest.getUsername();
        String roleName = userRoleRequest.getRole();

        User existingUser = userService.findByUsername(username);

        if (existingUser != null ) {
            // Remove the role from the user's roles
            User updatedUser = userService.removeUserRole(existingUser, roleName);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
	}/*
		 * @PostMapping("/CreateRole") public ResponseEntity<String>
		 * createRole(@RequestBody String roleName){ Role
		 * roleCreated=roleService.AddRole(roleName); if(roleCreated!=null) return
		 * ResponseEntity.status(HttpStatus.CREATED).body("Role registered successfully"
		 * ); else return ResponseEntity.status(HttpStatus.BAD_REQUEST).
		 * body("User registration failed."); }
		 */
}
