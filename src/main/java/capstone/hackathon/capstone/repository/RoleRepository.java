package capstone.hackathon.capstone.repository;

import java.util.Optional;

import capstone.hackathon.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.Role;
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findById(Long id);
	Optional<Role> findByName(String Name);
	//Optional<User> findByUserEmail(String userEmail);
}

