package capstone.hackathon.capstone.repository;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedIdeasRepository extends JpaRepository<AssignedIdeas,Integer> {
    List<AssignedIdeas> findByPanelistId(Long id);

}
