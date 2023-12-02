package capstone.hackathon.capstone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import capstone.hackathon.capstone.entities.User;
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByUserEmail(String userEmail);

	Optional<User> findById (Long id);




	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
	List<User> findUsersByRoleName(@Param("roleName") String roleName);


}
