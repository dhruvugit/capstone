package capstone.hackathon.capstone.service;

import java.util.*;
import java.util.stream.Collectors;

import capstone.hackathon.capstone.entities.User;
import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.AddScoreDto;
import capstone.hackathon.capstone.web.dto.ImplementationDto;

import capstone.hackathon.capstone.web.dto.TeamScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;



import capstone.hackathon.capstone.exceptions.*;

import capstone.hackathon.capstone.entities.Implementation;
import capstone.hackathon.capstone.entities.Team;
import capstone.hackathon.capstone.repository.ImplementationRepository;
import capstone.hackathon.capstone.repository.TeamRepository;
import jakarta.transaction.Transactional;

@Service("implementationService")
public class ImplementationService implements IfImplementationService{
	@Autowired 
	ImplementationRepository implementationRepository;
	TeamRepository teamRepository;

	@Autowired
	EmailService emailService;
	@Autowired
	UserService userService;
	@Autowired
    public ImplementationService(ImplementationRepository implementationRepository, TeamRepository teamRepository) {
        this.implementationRepository = implementationRepository;
        this.teamRepository = teamRepository;
    }

//    public Implementation createImplementationByTeamId(Long teamId) {
//        // Check if the team with the provided teamId exists
//        Team team = teamRepository.findById(teamId)
//                .orElseThrow(() -> new TeamNotFoundException("Team not found for id: " + teamId));
//
//        Implementation implementation = new Implementation();
//
//        // Set the Team for the Implementation
//        implementation.setTeam(team);
//
//        return implementationRepository.save(implementation);
//    }

	public Implementation submitImplementation(ImplementationDto implementationDto, UserInfoUserDetails user) {
		Team team = teamRepository.findByLeaderId(user.getId()).orElse(null);
		if (team == null) {
			// Handle the case when the user is not a leader of any team
			// Maybe throw an exception or return an error response
			return null;
		}


		Implementation implementation = new Implementation();
		implementation.setGitHubURL(implementationDto.getGitHubURL());
		implementation.setRecordingURL(implementationDto.getRecordingURL());
		implementation.setPptURL(implementationDto.getPptURL());
		implementation.setDescription(implementationDto.getDescription());
		implementation.setTeam(team);

		return implementationRepository.save(implementation);
	}




	@Override
	public Implementation createImplementation(Implementation implementation) {
		return implementationRepository.save(implementation);
	}

	@Override
	public List<Implementation> fetchAllImplementations() {
		return implementationRepository.findAll();
	}
	
	@Override
	public Implementation fetchImplementationById(int implementationId) {
		Optional<Implementation> optionalImplementation = implementationRepository.findById(implementationId);
		
		if(optionalImplementation.isPresent())
			return optionalImplementation.get();
		else
			throw new ImplementationNotFoundException("No Record Found For Id-"+ implementationId);
	}

	@Override
	public Implementation updateImplementationById(int implementationId, Implementation implementation) {
		if(implementationRepository.existsById(implementationId))
			return implementationRepository.save(implementation);
		else
			throw new ImplementationNotFoundException("No record Found for id-"+ implementationId);
	}

	@Override
	public String deleteImplementationById(int implementationId) {
		
		if(implementationRepository.existsById(implementationId))
		{
			implementationRepository.deleteById(implementationId);
			return "Deleted Successfully!!!";
		}
		else
			throw new ImplementationNotFoundException("No record Found for id-"+ implementationId);
	}

	@Override
	public Implementation findImplementationByTeamId(Long teamId) {
		Optional<Implementation> optionalImplementation = implementationRepository.findByTeam_TeamId(teamId);
        return optionalImplementation.orElseThrow(() -> new ImplementationNotFoundException("No Record Found For Team: " + teamId));
		/*    without lambda function for better explanation
		 * Optional<Implementation> optionalImplementation =
		 * implementationRepository.findByTeam_TeamId(teamId);
		 * 
		 * if (optionalImplementation.isPresent()) { return
		 * optionalImplementation.get(); } else { throw new
		 * ImplementationNotFoundException("No Record Found For Team: " + teamId); }
		 */
	}

	@Transactional
    public void updateImplementationFields(Long teamId, String newField1, String newField2, String newField3, String newField4) {
        implementationRepository.updateImplementationFields(teamId, newField1, newField2, newField3,newField4);
    }
	
//    public void updateScoreList(Long teamId, List<Integer> scoreList) {
//        // You can implement the logic here to update the score list for the given teamId.
//        // You might need to fetch the Implementation entity by teamId and update its score list.
//
//    	 Optional<Implementation> optionalImplementation = implementationRepository.findByTeam_TeamId(teamId);
//         if (optionalImplementation.isPresent()) {
//             Implementation implementation = optionalImplementation.get();
//             implementation.setScore(scoreList);
//             implementationRepository.save(implementation);
//         } else {
//             throw new TeamNotFoundException("No Implementation found for Team ID: " + teamId);
//         }
//    }

	@Override
	public void addScores(AddScoreDto addScoreDto) {
		String implementationId = addScoreDto.getImplementationId();
		List<Integer> scores = addScoreDto.getScores();

		Implementation implementation = implementationRepository.findById(Integer.parseInt(implementationId)).orElse(null);

		if (implementation != null) {
			implementation.getScore().addAll(scores);
			implementationRepository.save(implementation);

			// Send the feedback via email to the leader
			User user= userService.findByUserId(implementation.getTeam().getLeaderId());
			String leaderEmail = user.getUserEmail();
			String strengthFeedback = addScoreDto.getStrengthFeedback();
			String weaknessFeedback = addScoreDto.getWeaknessFeedback();
			String developmentFeedback = addScoreDto.getDevelopmentFeedback();

			sendFeedbackEmail(leaderEmail, strengthFeedback, weaknessFeedback, developmentFeedback);
		} else {
			throw new ImplementationNotFoundException("No implementation found for ID: " + implementationId);
		}
	}


	public void sendFeedbackEmail(String leaderEmail, String strengthFeedback, String weaknessFeedback, String developmentFeedback) {
		// Assuming you have an EmailService with a sendMail method
		String subject = "Feedback for Implementation";
		String body = "Strengths: " + strengthFeedback + "\n" +
				"Weaknesses: " + weaknessFeedback + "\n" +
				"Development Areas: " + developmentFeedback;

		// Send the email
		emailService.sendMail(leaderEmail, subject, body);
	}


//	public List<Map<String, Object>> getTeamScores() {
//		List<Object[]> teamScoreObjects = implementationRepository.getTeamScores();
//
//		return teamScoreObjects.stream().map(objects -> {
//			Implementation implementation = (Implementation) objects[0];
//			int totalScore = ((Number) objects[1]).intValue();
//
//			Map<String, Object> response = Map.of(
//					"implementation", implementation,
//					"totalScore", totalScore
//			);
//
//			return response;
//		}).collect(Collectors.toList());
//	}





	public List<TeamScoreResponse> getTeamScores() {
		List<Implementation> implementations = implementationRepository.findAll();
		List<TeamScoreResponse> teamScores = new ArrayList<>();
		for (Implementation implementation : implementations) {
			int sum = implementation.getScore().stream().mapToInt(Integer::intValue).sum();
			TeamScoreResponse teamScore = new TeamScoreResponse(implementation.getTeam().getTeamName(), sum);
			teamScores.add(teamScore);
		}
		teamScores.sort(Comparator.comparingInt(TeamScoreResponse::getScore).reversed());
		return teamScores;

	}



	
}
