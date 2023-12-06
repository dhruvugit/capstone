package capstone.hackathon.capstone.service;

import java.util.List;
import java.util.Map;

import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.web.dto.AddScoreDto;
import capstone.hackathon.capstone.web.dto.ImplementationDto;
import capstone.hackathon.capstone.web.dto.TeamScoreResponse;
import org.springframework.data.repository.query.Param;

import capstone.hackathon.capstone.entities.Implementation;


public interface IfImplementationService {
		public Implementation createImplementation(Implementation implementation);
		public List<Implementation> fetchAllImplementations();
		public Implementation fetchImplementationById(int implementationId);
		public Implementation updateImplementationById(int implementationId, Implementation implementation);
		public String deleteImplementationById(int implementationId);

	public void addScores(AddScoreDto addScoreDto);

	public Implementation submitImplementation(ImplementationDto implementationDto, UserInfoUserDetails user);

	public String judgeStatus(Long judgeId, int implementationId);
		public Implementation findImplementationByTeamId(Long teamId);
       //public List<Map<String, Object>> getTeamScores();
	public void updateImplementationFields(Long teamId, String newField1, String newField2,String newField3, String newField4) ;
		//public void updateScoreList(Long teamId, List<Integer> scoreList);


	public void sendFeedbackEmail(String leaderEmail, String strengthFeedback, String weaknessFeedback, String developmentFeedback);
	public List<TeamScoreResponse> getTeamScores();

}
