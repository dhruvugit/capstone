package capstone.hackathon.capstone.repository;

import capstone.hackathon.capstone.entities.AssignedIdeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignedIdeasRepository extends JpaRepository<AssignedIdeas,Integer> {
  //  List<AssignedIdeas> findByIdeaId(int id);

   // List<AssignedIdeas> findByIdeaId(int id);

    Optional<AssignedIdeas> findByIdeaId(int id);

    List<AssignedIdeas> findByPanelistId(Long id);

}
